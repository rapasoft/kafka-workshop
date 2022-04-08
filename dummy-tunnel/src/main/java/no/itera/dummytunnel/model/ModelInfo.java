package no.itera.dummytunnel.model;

import java.util.List;

public record ModelInfo(
        List<VariableType> variableTypes, List<Variable> variables, List<DeviceType> deviceTypes
) {
}
