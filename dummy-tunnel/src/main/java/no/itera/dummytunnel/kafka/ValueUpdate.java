package no.itera.dummytunnel.kafka;

public record ValueUpdate(
        long deviceId,
        String deviceName,
        long plantId,
        String plantName,
        double value,
        long timestamp
) {
}
