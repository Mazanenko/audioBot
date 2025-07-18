package ru.tgbot.audiobot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tgbot.audiobot.dto.api.TextResponseDto;
import ru.tgbot.audiobot.service.converter.audiototext.AudioToTextConverterService;

@Tag(name = "AudioToTextController", description = "Controller for converting audio messages to txt")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/convert/audio-to-text")
public class AudioToTextController {

    private final AudioToTextConverterService converterService;

    @Operation(
            summary = "Convert audio to text",
            description = "Converting audio file to text representation"
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TextResponseDto> convert(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(new TextResponseDto(converterService.convert(file)));
    }
}

