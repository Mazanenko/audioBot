package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tgbot.audiobot.enums.gigachat.FinishReason;

public record ChoiceDto(
        MessageDto message,
        Integer index,
        @JsonProperty("finish_reason") FinishReason finishReason
) {
}
