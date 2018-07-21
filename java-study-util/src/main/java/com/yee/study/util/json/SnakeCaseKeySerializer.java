package com.yee.study.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yee.study.util.CharacterUtil;

import java.io.IOException;

/**
 *
 */
public class SnakeCaseKeySerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        String key = CharacterUtil.snakeCase(value);
        gen.writeFieldName(key);
    }
}
