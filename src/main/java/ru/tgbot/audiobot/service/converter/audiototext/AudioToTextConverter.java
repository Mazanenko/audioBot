package ru.tgbot.audiobot.service.converter.audiototext;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface AudioToTextConverter {

    boolean isMatch(@NonNull MultipartFile file);

    /**
     * Convert givet audio file to text
     *
     * @param file audio file
     * @return text representation
     */
    @Nullable
    String convert(@NonNull MultipartFile file);
}
