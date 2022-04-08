package no.itera.dummytunnel.model;

public record Device(
        long deviceId,
        DeviceType deviceType,
        String name
) {
}
