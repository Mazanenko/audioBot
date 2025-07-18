package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tgbot.audiobot.enums.gigachat.Role;

import java.time.Instant;
import java.util.UUID;

public record Message(
        Role role,
        String content,
        Instant created,
        String name,
        @JsonProperty("functions_state_id") UUID functionsStateId,
        @JsonProperty("function_call") FunctionCall functionCall
) {
}
