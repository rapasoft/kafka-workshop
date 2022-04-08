package no.itera.dummytunnel.kafka;

public record StateUpdate(
        long deviceId,
        String deviceName,
        long plantId,
        String plantName,
        String variable,
        String state,
        AlarmLevel alarmLevel,
        long timestamp
) {
}
