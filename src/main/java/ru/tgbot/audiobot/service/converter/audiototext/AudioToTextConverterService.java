package ru.tgbot.audiobot.service;

import org.springframework.web.multipart.MultipartFile;

public interface AudioToTextConverterService {
    String convert(MultipartFile file);
}
