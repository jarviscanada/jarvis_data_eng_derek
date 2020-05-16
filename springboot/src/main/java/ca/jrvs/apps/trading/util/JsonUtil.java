package ca.jrvs.apps.trading.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {
    public static String toJson(Object object, boolean prettyJson, boolean includeNullVales) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        if (!includeNullVales) {
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (prettyJson) {
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return m.writeValueAsString(object);
    }

    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper m = new ObjectMapper();
        m.configure((DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES), false);
        return (T) m.readValue(json, clazz);
    }
}