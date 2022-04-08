package no.itera.dummytunnel.dto;

import java.util.List;

public record PlantDto(
    long plantId, String name, List<DeviceDto> devices
) {
}
