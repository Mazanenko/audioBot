package ru.tgbot.audiobot.dto.gigachat;

import java.util.List;

public record RequestBodyDto(
        String model,
        List<MessageDto> messages,

) {
}
