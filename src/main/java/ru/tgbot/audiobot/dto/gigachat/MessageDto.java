package ru.tgbot.audiobot.dto.gigachat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.tgbot.audiobot.enums.gigachat.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MessageDto {
    private Role role;
    private String content;
    private Instant created;
    private String name;
    @JsonProperty("functions_state_id")
    private UUID functionsStateId;
    @JsonProperty("function_call")
    private FunctionCallDto functionCall;
    private List<String> attachments;
}
