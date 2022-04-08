package no.itera.dummytunnel.model;

import java.util.List;

public record DeviceType(
        long deviceTypeId, String description, List<Variable> supportedVariables

) {
}
