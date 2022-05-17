package no.itera.kafkaworkshop;

public record ValueUpdate(
        long deviceId,
        String deviceName,
        long plantId,
        String plantName,
        double value,
        long timestamp
) {
}
