package ru.tgbot.audiobot.dto.gigachat.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tgbot.audiobot.enums.gigachat.AccessPolicy;
import ru.tgbot.audiobot.enums.gigachat.FilePurpose;

import java.time.Instant;
import java.util.UUID;

public record FileUploadResponseDto(
        Long bytes,
        @JsonProperty("created_at") Instant createdAt,
        String filename,
        UUID id,
        String object,
        FilePurpose purpose,
        @JsonProperty("access_policy") AccessPolicy accessPolicy
) {
}
