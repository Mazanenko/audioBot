package ru.tgbot.audiobot.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.tgbot.audiobot.config.feign.GigaChatFeignClientConfig;
import ru.tgbot.audiobot.dto.gigachat.ChatCompletionResponseDto;
import ru.tgbot.audiobot.dto.gigachat.ChatRequestDto;
import ru.tgbot.audiobot.dto.gigachat.attachment.FileUploadResponseDto;

@FeignClient(value = "GigaChatFeignClient", url = "${gigachat.url}", configuration = GigaChatFeignClientConfig.class)
public interface GigaChatFeignClient {

    @PostMapping(value = "/api/v1/chat/completions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ChatCompletionResponseDto> postToChat(@RequestBody ChatRequestDto chatRequestDto);

    @PostMapping(value = "/api/v1/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileUploadResponseDto> uploadFile(@RequestPart String purpose, @RequestPart MultipartFile file);
}
