package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_at") long expiresAt
) {
}
