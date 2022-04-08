package no.itera.dummytunnel.simulator;

import no.itera.dummytunnel.model.Plant;
import no.itera.dummytunnel.util.ModelInfoUtils;
import no.itera.dummytunnel.util.PlantGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class PlantsHolder {

    public static final int UPPER_BOUND = 100;
    private static final Logger logger = LoggerFactory.getLogger(PlantsHolder.class);

    private final List<Plant> plants;

    public PlantsHolder(
            @Autowired ModelInfoUtils modelInfoUtils,
            @Autowired PlantGenerator plantGenerator
    ) {
        var modelInfo = modelInfoUtils.loadFromJson("model_info.json");
        var random = new Random();

        var numberOfEmergencyBooths = random.nextInt(UPPER_BOUND);
        var numberOfCoSensors = random.nextInt(UPPER_BOUND);
        var numberOfNoSensors = random.nextInt(UPPER_BOUND);

        this.plants = IntStream.rangeClosed(1, 10).mapToObj(i -> {
            String plantName = "Test" + i;
            return plantGenerator.generatePlant(
                    i,
                    plantName,
                    modelInfo,
                    numberOfEmergencyBooths,
                    numberOfCoSensors,
                    numberOfNoSensors
            );
        }).toList();

        logger.info("Generated {} plants with {} emergency booths, {} co sensors and {} no sensors.",
                plants.size(), numberOfEmergencyBooths, numberOfCoSensors, numberOfNoSensors);
    }

    public List<Plant> listPlants() {
        return plants;
    }

}
