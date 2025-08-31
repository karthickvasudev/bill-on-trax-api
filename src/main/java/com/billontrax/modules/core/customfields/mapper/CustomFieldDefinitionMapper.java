package com.billontrax.modules.core.customfields.mapper;

import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomFieldDefinitionMapper {
    
    CustomFieldDefinitionDto toDto(CustomFieldDefinition entity);

    CustomFieldDefinition toEntity(CustomFieldDefinitionDto dto);

    void updateFromDto(CustomFieldDefinitionDto dto, @MappingTarget CustomFieldDefinition entity);
}

