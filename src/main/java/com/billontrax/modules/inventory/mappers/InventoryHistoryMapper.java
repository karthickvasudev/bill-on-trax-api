package com.billontrax.modules.inventory.mappers;

import com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryHistoryDto;
import com.billontrax.modules.inventory.entities.InventoryHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryHistoryMapper {
    InventoryHistory toEntity(InventoryHistoryCreateRequest req);
    InventoryHistoryDto toDto(InventoryHistory entity);
}

