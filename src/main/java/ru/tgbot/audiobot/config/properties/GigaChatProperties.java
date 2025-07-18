package ru.tgbot.audiobot.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "gigachat")
public class GigaChatProperties {
    private String url;
    private String authUrl;
    private String model;
    private String scope;
    private String authKey;
    private UploadSize uploadSize = new UploadSize();
    private boolean audioToTextConverterEnabled;


    @Getter
    @Setter
    public static class UploadSize {
        /**
         * max audio file size in megabytes
         */
        private int maxAudioFileSize = 35;

        /**
         * max image file size in megabytes
         */
        private int maxImageFileSize = 15;

        /**
         * max text file size in megabytes
         */
        private int maxTextFileSize = 40;
    }
}
