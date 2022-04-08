package no.itera.dummytunnel.util;

import no.itera.dummytunnel.model.Device;
import no.itera.dummytunnel.model.ModelInfo;
import no.itera.dummytunnel.model.Plant;
import no.itera.dummytunnel.model.Variable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PlantGenerator {

    public Plant generatePlant(
            long plantId,
            String plantName,
            ModelInfo modelInfo,
            int numberOfEmergencyBooths,
            int numberOfCoSensors,
            int numberOfNoSensors
    ) {
        var devices = new ArrayList<Device>();

        var emergencyBoothIndexRangeStart = (int) plantId * 1000;
        var coSensorIndexRangeStart = emergencyBoothIndexRangeStart + numberOfEmergencyBooths + 1;
        var noSensorIndexRangeStart = emergencyBoothIndexRangeStart + numberOfCoSensors + numberOfEmergencyBooths + 1;

        devices.addAll(IntStream.rangeClosed(emergencyBoothIndexRangeStart, emergencyBoothIndexRangeStart + numberOfEmergencyBooths).mapToObj(i -> new Device(
                i, modelInfo.deviceTypes().stream().filter(deviceType -> deviceType.deviceTypeId() == 1).findFirst().orElseThrow(),
                "Emergency Booth " + i
        )).toList());
        devices.addAll(IntStream.rangeClosed(coSensorIndexRangeStart, coSensorIndexRangeStart + numberOfCoSensors).mapToObj(i -> new Device(
                i, modelInfo.deviceTypes().stream().filter(deviceType -> deviceType.deviceTypeId() == 2).findFirst().orElseThrow(),
                "CO Sensor " + i)).toList());
        devices.addAll(IntStream.rangeClosed(noSensorIndexRangeStart + 1, noSensorIndexRangeStart + numberOfNoSensors).mapToObj(i -> new Device(
                i, modelInfo.deviceTypes().stream().filter(deviceType -> deviceType.deviceTypeId() == 3).findFirst().orElseThrow(),
                "NO Sensor" + i)).toList());


        var defaultStates = devices.stream()
                .filter(device -> device.deviceType().supportedVariables().stream().anyMatch(variable -> variable.type().name().equals("status")))
                .collect(Collectors.toMap(
                        Device::deviceId,
                        device -> device.deviceType().supportedVariables().stream()
                                .filter(variable -> variable.type().name().equals("status"))
                                .collect(Collectors.toMap(Variable::variableId, variable -> variable.supportedStates().get(0).name()))
                ));
        var defaultAnalogValues = devices.stream()
                .filter(device -> device.deviceType().supportedVariables().stream().anyMatch(variable -> variable.type().name().equals("analogValue")))
                .collect(Collectors.toMap(Device::deviceId, device -> 0.0));

        return new Plant(
                plantId,
                plantName,
                devices,
                defaultStates,
                defaultAnalogValues

        );
    }

}
