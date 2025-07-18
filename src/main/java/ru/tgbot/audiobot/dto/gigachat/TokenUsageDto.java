package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenUsageDto(
        @JsonProperty("prompt_tokens") Integer promptTokens,
        @JsonProperty("completion_tokens") Integer completionTokens,
        @JsonProperty("precached_prompt_tokens") Integer precachedPromptTokens,
        @JsonProperty("total_tokens") Integer totalTokens
) {
}
