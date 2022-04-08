package no.itera.dummytunnel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.itera.dummytunnel.model.ModelInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ModelInfoUtils {

    public final ObjectMapper objectMapper;

    public ModelInfoUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public ModelInfo loadFromJson(String modelInfoClassPath) {
        try {
            return objectMapper.readValue(getClass().getResource("/model_info.json"), ModelInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
