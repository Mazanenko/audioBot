package ru.tgbot.audiobot.enums.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum FilePurpose {
    @JsonProperty("general") GENERAL("general");

    private final String value;

    FilePurpose(String value) {
        this.value = value;
    }
}
