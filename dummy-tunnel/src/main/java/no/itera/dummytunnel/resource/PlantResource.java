package no.itera.dummytunnel.resource;

import no.itera.dummytunnel.dto.PlantDto;
import no.itera.dummytunnel.mapper.PlantMapper;
import no.itera.dummytunnel.simulator.PlantsHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/plant", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlantResource {

    private final PlantsHolder plantsHolder;
    private final PlantMapper plantMapper;

    public PlantResource(PlantsHolder plantsHolder) {
        this.plantsHolder = plantsHolder;
        this.plantMapper = PlantMapper.INSTANCE;
    }

    @GetMapping("/")
    public List<PlantDto> listPlants() {
        return plantsHolder.listPlants().stream().map(plantMapper::mapPlant).toList();
    }

    @GetMapping("/{plantId}")
    public Optional<PlantDto> getPlant(@PathVariable Long plantId) {
        return plantsHolder.listPlants()
                .stream().filter(plant -> plant.plantId() == plantId)
                .map(plantMapper::mapPlant)
                .findFirst();
    }

}
