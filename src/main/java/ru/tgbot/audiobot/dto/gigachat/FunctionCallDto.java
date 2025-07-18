package ru.tgbot.audiobot.dto.gigachat;

import java.util.Map;

public record FunctionCallDto(String name,
                              Map<String, Object> arguments) {
}
