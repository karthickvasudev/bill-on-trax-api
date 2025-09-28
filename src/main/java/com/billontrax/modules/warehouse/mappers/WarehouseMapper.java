package com.billontrax.modules.warehouse.mappers;

import com.billontrax.modules.warehouse.dtos.*;
import com.billontrax.modules.warehouse.entities.Warehouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseCreateRequest req);

    WarehouseDto toDto(Warehouse entity);

    WarehouseDetailDto toDetailDto(Warehouse entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(WarehouseUpdateRequest req, @MappingTarget Warehouse entity);
}

