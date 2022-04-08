package no.itera.dummytunnel.mapper;

import no.itera.dummytunnel.dto.DeviceDto;
import no.itera.dummytunnel.dto.PlantDto;
import no.itera.dummytunnel.model.Device;
import no.itera.dummytunnel.model.Plant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlantMapper {

    PlantMapper INSTANCE = Mappers.getMapper(PlantMapper.class);

    PlantDto mapPlant(Plant plant);

    @Mapping(target = "deviceType", source = "deviceType.description")
    DeviceDto mapDevice(Device deviceType);

}
