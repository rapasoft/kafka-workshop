package no.itera.dummytunnel.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonIdentityInfo(
        property = "variableId",
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        scope = Variable.class
)
public record Variable(
        long variableId, String name, VariableType type, List<State> supportedStates
) {
}
