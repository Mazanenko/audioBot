package ru.tgbot.audiobot.enums.gigachat;

import lombok.Getter;

import java.util.Collection;

@Getter
public enum MimeType {
    // Текстовые
    TXT("text/plain"),

    // Документы
    DOC("application/msword"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PDF("application/pdf"),
    EPUB("application/epub+zip"),

    // Презентации
    PPT("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),

    // Изображения
    JPEG("image/jpeg"),
    PNG("image/png"),
    TIFF("image/tiff"),
    BMP("image/bmp"),

    // Аудио
    MP4_AUDIO("audio/mp4"),
    MP3("audio/mpeg"),
    M4A("audio/x-m4a"),
    WAV("audio/wav"),  // Основной тип (остальные варианты удалены)
    WEBA("audio/webm"),
    OGG_AUDIO("audio/ogg"),
    OPUS("audio/opus");

    private final String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static boolean isMimeTypeEqualsAny(String mimeType, Collection<MimeType> mimeTypes) {
        for (MimeType type : mimeTypes) {
            if (mimeType.equals(type.getMimeType())) {
                return true;
            }
        }
        return false;
    }
}
