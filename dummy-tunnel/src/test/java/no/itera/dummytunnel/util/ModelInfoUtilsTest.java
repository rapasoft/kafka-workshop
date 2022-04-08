package no.itera.dummytunnel.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelInfoUtilsTest {

    @Test
    void loadFromJson() {
        var modelInfo = new ModelInfoUtils().loadFromJson("model_info.json");

        assertNotNull(modelInfo);
        System.out.println(modelInfo);
    }
}