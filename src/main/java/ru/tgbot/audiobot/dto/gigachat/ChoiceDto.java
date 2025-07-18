package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tgbot.audiobot.enums.gigachat.FinishReason;

public record Choice(
        Message message,
        Integer index,
        @JsonProperty("finish_reason") FinishReason finishReason
) {
}
