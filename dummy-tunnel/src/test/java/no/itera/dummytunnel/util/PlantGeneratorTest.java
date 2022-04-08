package no.itera.dummytunnel.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlantGeneratorTest {

    @Test
    void generatedPlant() {
        var modelInfo = new ModelInfoUtils().loadFromJson("model_info.json");
        var result = new PlantGenerator().generatePlant(1L, "Test", modelInfo, 30, 10, 10);

        assertNotNull(result);
        System.out.println(result);
    }
}