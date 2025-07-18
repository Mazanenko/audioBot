package ru.tgbot.audiobot.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tgbot.audiobot.config.feign.GigaChatAuthFeignClientConfig;
import ru.tgbot.audiobot.dto.gigachat.AuthResponseDto;

import java.util.Map;

@FeignClient(value = "GigaChatAuthFeignClient", url = "${gigachat.auth-url}", configuration = GigaChatAuthFeignClientConfig.class)
public interface GigaChatAuthFeignClient {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<AuthResponseDto> auth(@RequestBody Map<String, String> requestBody);
}
