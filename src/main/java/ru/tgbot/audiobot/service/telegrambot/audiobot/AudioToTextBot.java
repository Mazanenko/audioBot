package ru.tgbot.audiobot.service.telegrambot.audiobot;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tgbot.audiobot.config.properties.TelegramProperties;
import ru.tgbot.audiobot.service.converter.audiototext.AudioToTextConverterService;
import ru.tgbot.audiobot.util.exception.TelegramApiUtils;

import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class AudioToTextBot extends TelegramLongPollingBot {
    private final TelegramProperties properties;
    private final TelegramApiUtils telegramApiUtils;
    private final AudioToTextConverterService audioToTextConverterService;

    public AudioToTextBot(TelegramProperties properties, TelegramApiUtils telegramApiUtils, AudioToTextConverterService audioToTextConverterService) {
        super(properties.getToken());
        this.properties = properties;
        this.telegramApiUtils = telegramApiUtils;
        this.audioToTextConverterService = audioToTextConverterService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;

        var chatId = update.getMessage().getChatId();
        Message message = update.getMessage();

        if (!message.hasVoice() && !message.hasAudio()) {
            log.warn("Received a message that doesn't have an audio message");
            sendMessage(chatId, "I can only consume audio or voice");
            return;
        }
        if (message.hasVoice()) {
            handleVoiceMessage(message);
        }
    }


    @Override
    public String getBotUsername() {
        return properties.getBotName();
    }

    private void sendMessage(long chatId, @NonNull String text) {
        log.info("Sending message to chat: {}.", chatId);
        log.debug(" Message text: {}", text);

        var message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error while sending message to chat: {}.", chatId, e);
        }
    }

    private MultipartFile downloadVoiceFile(@NonNull String fileId) throws TelegramApiException, IOException {
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        var file = execute(getFile);

        String fileUrl = file.getFileUrl(getBotToken());
        var url = new URL(fileUrl);

        return telegramApiUtils.downloadFile(url);
    }

    private void handleVoiceMessage(Message message) {
        Voice voice = message.getVoice();
        long chatId = message.getChatId();

        String fileId = voice.getFileId();

        try {
            MultipartFile file = downloadVoiceFile(fileId);

            String response = String.format(
                    """
                            Получено голосовое сообщение!
                            Длительность: %d сек.
                            Тип: %s
                            Размер: %d байт.\s
                            Скоро пришлю расшифровку...
                            """,
                    voice.getDuration(),
                    voice.getMimeType(),
                    voice.getFileSize());

            sendMessage(chatId, response);

            var result = audioToTextConverterService.convert(file);
            sendMessage(chatId, "Расшифровал: \n" + result);

        } catch (Exception e) {
            sendMessage(chatId, "Ошибка обработки сообщения");
            log.error("Ошибка обработки сообщения для чата: %s \n %s".formatted(chatId, e.getLocalizedMessage()), e);
        }
    }
}
