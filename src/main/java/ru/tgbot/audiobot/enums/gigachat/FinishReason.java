package ru.tgbot.audiobot.enums.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FinishReason {
    @JsonProperty("stop") STOP,
    @JsonProperty("length") LENGTH,
    @JsonProperty("function_call") FUNCTION_CALL,
    @JsonProperty("blacklist") BLACKLIST,
    @JsonProperty("error") ERROR
}
