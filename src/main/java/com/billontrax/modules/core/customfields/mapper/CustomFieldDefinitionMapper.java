package com.billontrax.modules.core.customfields.mapper;

import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomFieldDefinitionMapper {
    CustomFieldDefinitionDto toDto(CustomFieldDefinition entity);

    CustomFieldDefinition toEntity(CustomFieldDefinitionDto dto);
}
