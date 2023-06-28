package rsb.rsocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:15:09
 * @since 2022.04.0
 */
public class EncodingUtils {

    private final ObjectMapper objectMapper;

    private final ObjectReader objectReader;

    private final TypeReference<Map<String,Object>> typeReference = new TypeReference<>(){};

    public EncodingUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectReader = this.objectMapper.readerFor(typeReference);
    }

    public <T> T decode(String json, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String encode(T object)  {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String encodeMetadata(Map<String,Object> metadata) {
        try {
            return this.objectMapper.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Object> decodeMetadata(String json) {
        try {
            return this.objectReader.readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
