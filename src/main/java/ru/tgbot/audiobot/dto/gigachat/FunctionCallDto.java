package ru.tgbot.audiobot.dto.gigachat;

import java.util.Map;

public record FunctionCall(String name,
                           Map<String, Object> arguments) {
}
