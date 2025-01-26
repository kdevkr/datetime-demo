package kr.kdev.demo.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration(proxyBeanMethods = false)
public class JsonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .modules(new JavaTimeModule())
                .deserializerByType(OffsetDateTime.class, new OffsetDateTimeDeserializer());
    }

    private static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String str = jsonParser.getValueAsString();
            if (str == null || str.isEmpty()) {
                return null;
            }

            if (str.matches("^\\d+$")) {
                long value = jsonParser.getNumberValue().longValue();
                Instant instant = Instant.ofEpochMilli(value);
                return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
            }

            try {
                return OffsetDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            } catch (DateTimeException e) {
                return OffsetDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS").withZone(ZoneOffset.UTC));
            }
        }
    }
}
