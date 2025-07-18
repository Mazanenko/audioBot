package ru.tgbot.audiobot.enums.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccessPolicy {
    @JsonProperty("public") PUBLIC,
    @JsonProperty("private") PRIVATE
}
