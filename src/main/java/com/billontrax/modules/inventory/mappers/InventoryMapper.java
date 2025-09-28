package com.billontrax.modules.inventory.mappers;

import com.billontrax.modules.inventory.dtos.InventoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryDto;
import com.billontrax.modules.inventory.dtos.InventoryUpdateRequest;
import com.billontrax.modules.inventory.entities.Inventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryCreateRequest req);

    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "warehouseActive", source = "warehouse.isActive")
    InventoryDto toDto(Inventory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(InventoryUpdateRequest req, @MappingTarget Inventory entity);
}
