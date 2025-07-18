package ru.tgbot.audiobot.enums.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("assistant") ASSISTANT,
    @JsonProperty("system") SYSTEM,
    @JsonProperty("user") USER,
    @JsonProperty("function") FUNCTION
}
