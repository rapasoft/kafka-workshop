package no.itera.dummytunnel.simulator;

import no.itera.dummytunnel.kafka.StateUpdate;
import no.itera.dummytunnel.kafka.ValueUpdate;
import no.itera.dummytunnel.model.State;
import no.itera.dummytunnel.model.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@DependsOn("valueUpdateKafkaTemplate")
public class MainSimulation {

    private static final Logger logger = LoggerFactory.getLogger(MainSimulation.class);
    private final PlantsHolder plantsHolder;
    private final CommonVariables commonVariables;
    private final KafkaTemplate<Long, ValueUpdate> valueUpdateKafkaTemplate;
    private final KafkaTemplate<Long, StateUpdate> stateUpdateKafkaTemplate;
    @Value("${valueUpdateTopic.name}")
    private String valueUpdateTopic;
    @Value("${stateUpdateTopic.name}")
    private String stateUpdateTopic;

    @Autowired
    public MainSimulation(
            PlantsHolder plantsHolder,
            CommonVariables commonVariables,
            @Qualifier("valueUpdateKafkaTemplate") KafkaTemplate<Long, ValueUpdate> valueUpdateKafkaTemplate,
            @Qualifier("stateUpdateKafkaTemplate") KafkaTemplate<Long, StateUpdate> stateUpdateKafkaTemplate) {
        this.plantsHolder = plantsHolder;
        this.commonVariables = commonVariables;
        this.valueUpdateKafkaTemplate = valueUpdateKafkaTemplate;
        this.stateUpdateKafkaTemplate = stateUpdateKafkaTemplate;
    }

    @Scheduled(fixedRateString = "${plant.analogValues.simulationRate:100}")
    public void simulateAnalogValues() {
        if (commonVariables.isSimulationStarted()) {
            plantsHolder.listPlants().forEach(plant -> {
                for (Long deviceId : plant.deviceAnalogValues().keySet()) {
                    double newValue = Math.random() * 10.0;
                    plant.deviceAnalogValues().put(deviceId, newValue);
                    logger.debug("Sending analog value {} for device id {}", newValue, deviceId);
                    var device = plant.devices().stream().filter(d -> d.deviceId() == deviceId).findFirst().orElseThrow();
                    valueUpdateKafkaTemplate.send(valueUpdateTopic, plant.plantId(), new ValueUpdate(
                            deviceId, device.name(), plant.plantId(), plant.name(), newValue, Instant.now().toEpochMilli()
                    ));
                }
            });
        }
    }

    @Scheduled(fixedRateString = "${plant.stateUpdate.simulationRate:10000}")
    public void simulateStateUpdates() {
        if (commonVariables.isSimulationStarted()) {
            plantsHolder.listPlants().forEach(plant -> {
                Map<Long, Map<Long, String>> deviceVariableStates = plant.deviceVariableStates();
                for (Long deviceId : deviceVariableStates.keySet()) {
                    Map<Long, String> variableStates = deviceVariableStates.get(deviceId);
                    for (Long variableId : variableStates.keySet()) {
                        if (Math.random() > 0.99) {
                            var device = plant.devices().stream()
                                    .filter(d -> d.deviceId() == deviceId)
                                    .findFirst().orElseThrow();

                            var deviceType = device.deviceType();

                            List<State> supportedStates = new ArrayList<>(deviceType.supportedVariables().stream()
                                    .filter(variable -> variable.variableId() == variableId)
                                    .map(Variable::supportedStates).flatMap(Collection::stream)
                                    .toList());
                            Collections.shuffle(supportedStates);
                            var newRandomState = supportedStates.get(0);

                            var variableName = deviceType.supportedVariables().stream()
                                    .filter(variable -> variable.variableId() == variableId)
                                    .map(Variable::name)
                                    .findFirst().orElseThrow();

                            String oldState = variableStates.get(variableId);

                            if (!oldState.equals(newRandomState.name())) {
                                variableStates.put(variableId, newRandomState.name());

                                plant.deviceVariableStates().put(deviceId, variableStates);

                                stateUpdateKafkaTemplate.send(
                                        stateUpdateTopic,
                                        plant.plantId(),
                                        new StateUpdate(
                                                deviceId,
                                                device.name(),
                                                plant.plantId(),
                                                plant.name(),
                                                variableName,
                                                newRandomState.name(),
                                                newRandomState.alarmLevel(),
                                                Instant.now().toEpochMilli()
                                        )
                                );
                            }
                        }
                    }
                }
            });
        }
    }

}
