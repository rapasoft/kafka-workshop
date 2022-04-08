package no.itera.dummytunnel.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        property = "variableTypeId",
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        scope = VariableType.class
)
public record VariableType(
        long variableTypeId, String name
) {
}
