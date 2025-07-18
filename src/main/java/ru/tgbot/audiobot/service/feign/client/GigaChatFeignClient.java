package ru.tgbot.audiobot.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tgbot.audiobot.dto.gigachat.ChatCompletionResponseDto;
import ru.tgbot.audiobot.dto.gigachat.attachment.FileUploadRequestDto;
import ru.tgbot.audiobot.dto.gigachat.attachment.FileUploadResponseDto;

@FeignClient(value = "GigaChatFeignClient", url = "${gigachat.url}")
public interface GigaChatFeignClient {

    ChatCompletionResponseDto postToChat();

    @PostMapping(value = "/api/v1/files", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileUploadResponseDto uploadFile(FileUploadRequestDto request);
}
