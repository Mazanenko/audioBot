package ru.tgbot.audiobot.dto.gigachat;

import java.time.Instant;
import java.util.List;

public record ChatCompletionResponse(
        String object,
        Instant created,
        String model,
        List<Choice> choices,
        TokenUsage usage
) {
}
