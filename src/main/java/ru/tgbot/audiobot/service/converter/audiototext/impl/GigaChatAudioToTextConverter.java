package ru.tgbot.audiobot.service.converter.audiototext.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import ru.tgbot.audiobot.config.properties.GigaChatProperties;
import ru.tgbot.audiobot.dto.gigachat.ChatRequestDto;
import ru.tgbot.audiobot.dto.gigachat.MessageDto;
import ru.tgbot.audiobot.enums.gigachat.FilePurpose;
import ru.tgbot.audiobot.enums.gigachat.MimeType;
import ru.tgbot.audiobot.enums.gigachat.Role;
import ru.tgbot.audiobot.service.feign.client.GigaChatFeignClient;

import java.util.List;
import java.util.Set;

import static ru.tgbot.audiobot.enums.gigachat.MimeType.*;

@ConditionalOnBooleanProperty("gigachat.audio-to-text-converter-enabled")
@Service
@RequiredArgsConstructor
@Slf4j
public class GigaChatAudioToTextConverter extends AbstractAudioToTextConverter {
    private final GigaChatProperties properties;
    private final GigaChatFeignClient gigaChatFeignClient;

    private static final String PROMPT = "Идентификатор аудиофайла для расшифровки находится в блоке attachments";
    private static final MessageDto SYSTEM_PROMPT = MessageDto.builder()
            .role(Role.SYSTEM)
            .content(SYSTEM_PROMPT_TEXT)
            .build();

    @Override
    protected Set<MimeType> getValidContentTypes() {
        return Set.of(MP4_AUDIO, MP3, M4A, WAV, WEBA, OGG_AUDIO, OPUS);
    }

    @Override
    public boolean isMatch(@NotNull MultipartFile file) {
        var fileSize = DataSize.ofBytes(file.getSize());
        final var maxFileSize = DataSize.ofMegabytes(properties.getUploadSize().getMaxAudioFileSize());

        return maxFileSize.compareTo(fileSize) >= 0 && contentTypeIsMatch(file.getContentType());
    }

    @Override
    public String convert(@NotNull MultipartFile file) {
        var fileUploadResponse = gigaChatFeignClient.uploadFile(FilePurpose.GENERAL.getValue(), file);

        if (!fileUploadResponse.getStatusCode().is2xxSuccessful() || fileUploadResponse.getBody() == null) {
            log.error("Failed to upload file: {}", fileUploadResponse.getBody());
            throw new RuntimeException("Failed to upload file: " + fileUploadResponse.getBody());
        }

        var fileUploadResponseBody = fileUploadResponse.getBody();

        var messageDto = MessageDto.builder()
                .role(Role.USER)
                .content(PROMPT)
                .attachments(List.of(fileUploadResponseBody.id().toString()))
                .build();

        var chatResponseEntity = gigaChatFeignClient.postToChat(
                new ChatRequestDto(properties.getModel(), List.of(SYSTEM_PROMPT, messageDto)));

        if (!chatResponseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to post chat: {}", chatResponseEntity.getBody());
            throw new RuntimeException("Failed to post chat: " + chatResponseEntity.getBody());
        }

        if (chatResponseEntity.getBody() == null || CollectionUtils.isEmpty(chatResponseEntity.getBody().choices())) {
            log.warn("Chat returned null");
            return null;
        }

        return chatResponseEntity.getBody().choices().stream()
                .filter(e -> e.index() != null && e.index().equals(0) && e.message() != null)
                .map(e -> e.message().getContent())
                .findFirst().orElse(null);
    }
}
