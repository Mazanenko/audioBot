package ru.tgbot.audiobot.service.converter.audiototext.impl;

import org.springframework.lang.Nullable;
import ru.tgbot.audiobot.enums.gigachat.MimeType;
import ru.tgbot.audiobot.service.converter.audiototext.AudioToTextConverter;

import java.util.Set;

public abstract class AbstractAudioToTextConverter implements AudioToTextConverter {
    protected static final String SYSTEM_PROMPT_TEXT = """
            Требуется максимально точная расшифровка аудиосообщения в текст.
            
            Основные требования:
            1. **Язык**: Преимущественно русский, но встречаются английские слова/фразы – их нужно сохранять в оригинале (без транслитерации).
            2. **Голос**: В одном сообщении только один говорящий. Речь может быть разборчивой или с легкими дефектами (например, шепот, небольшие паузы).
            3. **Шумы**: Игнорировать фоновые шумы (стук, скрип, фоновую музыку, разговоры других людей), сохраняя только речь целевого голоса.
            4. **Форматирование**:
               - Сохраняй пунктуацию (точки, запятые, вопросительные знаки) там, где это уместно.
               - Не добавляй посторонние комментарии (например, "[неразборчиво]", если речь четкая).
               - Слова с неочевидным произношением можно записать в вариантах: "к примеру [возможно: например]".
            5. **Контекст**: Если в речи есть специфические термины, имена или аббревиатуры – старайся передать их точно, даже если они звучат непривычно.
            
            Задача: Сделать текст максимально близким к оригинальной речи, без искажений и пропусков.
            """;


    protected abstract Set<MimeType> getValidContentTypes();

    protected boolean contentTypeIsMatch(@Nullable String contentType) {
        if (contentType == null) return false;
        return MimeType.isMimeTypeEqualsAny(contentType, getValidContentTypes());
    }
}
