package ru.tgbot.audiobot.util.exception;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.tgbot.audiobot.entity.CustomMultipartFile;
import ru.tgbot.audiobot.enums.gigachat.MimeType;

import java.io.*;
import java.net.URL;

@Component
public class TelegramApiUtils {


    public MultipartFile downloadFile(@NonNull URL url) throws IOException {
        byte[] content;

        try (InputStream in = url.openStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            content = out.toByteArray();
        }
        return new CustomMultipartFile(content, "voice_message", MimeType.OGG_AUDIO.getMimeType());
    }
}
