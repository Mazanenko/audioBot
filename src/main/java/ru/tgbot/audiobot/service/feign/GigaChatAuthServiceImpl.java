package ru.tgbot.audiobot.service.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GigaChatAuthService {
    private final static long STOCK_TIME_MINUTE = 1L;

    private String token;
    private String refresh;
    private Instant expires;

    public String getCurrentToken() {
        if (Objects.isNull(token) || isExpire()) refresh();
        return token;
    }

    private Boolean isExpire() {
        if (Objects.isNull(expires)) return true;
        return !expires.isAfter(Instant.now().plus(STOCK_TIME_MINUTE, ChronoUnit.SECONDS));
    }

    private void refresh() {
        PFORequestAuth request = new PFORequestAuth(properties);
        PFOResponseAuth response = restClient.auth(request).getBody();

        if(Objects.isNull(response) || Objects.isNull(response.getToken())) {
            throw new RuntimeException("Не корректный ответ от сервера GigaChat при попытке авторизации");
        }

        PFOResponseAuth.Token data = response.getToken();
        token = data.getAccessToken();
        refresh = data.getRefreshToken();

        Optional<PFOJwtData> jwtData = Arrays.stream(token.split(JWT_DELIMITER))
                .skip(SKIP_POSITION)
                .limit(LIMIT_POSITION)
                .map(Base64.getDecoder()::decode)
                .map(String::new)
                .findFirst()
                .map(item -> mapperUtils.mapping(item, PFOJwtData.class));

        if (jwtData.isEmpty()) {
            expires = LocalDateTime.now();
        } else {
            Instant instant = Instant.ofEpochSecond(jwtData.get().getExp());
            expires = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
        }
    }
}
