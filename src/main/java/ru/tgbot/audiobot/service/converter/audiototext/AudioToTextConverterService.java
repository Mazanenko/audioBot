package ru.tgbot.audiobot.service.converter.audiototext;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AudioToTextConverterService {
    private final List<AudioToTextConverter> audioToTextConverterList;

    public String convert(@NonNull MultipartFile file) {
        var converter = audioToTextConverterList.stream()
                .filter(e -> e.isMatch(file))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("AudioToText converter not found for file %s with type %s"
                        .formatted(file.getOriginalFilename(), file.getContentType())));

        return converter.convert(file);
    }
}
