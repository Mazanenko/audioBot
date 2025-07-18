package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatRequestDto(
        String model,
        List<MessageDto> messages,
        @JsonProperty("function_call") FunctionCallDto functionCall

) {
    public ChatRequestDto(String model, List<MessageDto> messages) {
        this(model, messages, null);
    }
}
