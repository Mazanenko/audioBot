package ru.tgbot.audiobot.service.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tgbot.audiobot.config.properties.GigaChatProperties;
import ru.tgbot.audiobot.dto.gigachat.AuthResponseDto;
import ru.tgbot.audiobot.service.feign.client.GigaChatAuthFeignClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GigaChatAuthServiceImpl implements GigaChatAuthService {
    private final static long STOCK_TIME_MINUTE = 1L;

    private String token;
    private Instant expires;

    private final GigaChatAuthFeignClient gigaChatAuthFeignClient;
    private final GigaChatProperties gigaChatProperties;

    public String getCurrentToken() {
        if (token == null || isExpire()) refresh();
        return token;
    }

    private Boolean isExpire() {
        if (expires == null) return true;
        return !expires.isAfter(Instant.now().plus(STOCK_TIME_MINUTE, ChronoUnit.SECONDS));
    }

    private void refresh() {
        AuthResponseDto response = gigaChatAuthFeignClient.auth(Map.of("scope", gigaChatProperties.getScope())).getBody();

        if (Objects.isNull(response) || Objects.isNull(response.accessToken())) {
            throw new RuntimeException("Не корректный ответ от сервера GigaChat при попытке авторизации");
        }
        token = response.accessToken();
        expires = Instant.ofEpochMilli(response.expiresAt());
    }
}
