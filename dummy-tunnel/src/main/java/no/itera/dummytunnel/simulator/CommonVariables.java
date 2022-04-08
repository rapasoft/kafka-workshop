package no.itera.dummytunnel.simulator;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CommonVariables {

    private final AtomicBoolean simulationStarted = new AtomicBoolean(true);

    public boolean isSimulationStarted() {
        return simulationStarted.get();
    }

    public void setSimulationStarted(boolean simulationStarted) {
        this.simulationStarted.set(simulationStarted);
    }

}
