package ru.tgbot.audiobot.dto.gigachat.attachment;

import ru.tgbot.audiobot.enums.gigachat.AccessPolicy;
import ru.tgbot.audiobot.enums.gigachat.FilePurpose;

import java.time.Instant;
import java.util.UUID;

public record FileUploadRresponseDto(
        Long bytes,
        Instant createdAt,
        String filename,
        UUID id,
        String object,
        FilePurpose purpose,
        AccessPolicy accessPolicy
) {
}
