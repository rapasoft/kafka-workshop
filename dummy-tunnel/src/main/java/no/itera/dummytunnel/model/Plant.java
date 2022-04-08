package no.itera.dummytunnel.model;

import java.util.List;
import java.util.Map;

public record Plant(
        long plantId,
        String name,
        List<Device> devices,
        Map<Long, Map<Long, String>> deviceVariableStates,
        Map<Long, Double> deviceAnalogValues
) {
}
