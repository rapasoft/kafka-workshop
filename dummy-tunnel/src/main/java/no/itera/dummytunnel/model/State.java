package no.itera.dummytunnel.model;

import no.itera.dummytunnel.kafka.AlarmLevel;

public record State(String name, AlarmLevel alarmLevel) {
}
