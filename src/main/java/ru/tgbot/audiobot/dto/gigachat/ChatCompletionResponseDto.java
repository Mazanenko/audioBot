package ru.tgbot.audiobot.dto.gigachat;

import java.time.Instant;
import java.util.List;

public record ChatCompletionResponseDto(
        String object,
        Instant created,
        String model,
        List<ChoiceDto> choices,
        TokenUsageDto usage
) {
}
