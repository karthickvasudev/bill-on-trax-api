package com.billontrax.modules.core.customfields.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;

@Mapper(componentModel = "spring")
public interface CustomFieldDefinitionMapper {
    @Mapping(target = "createdTime", source = "createdTime")
    @Mapping(target = "updatedTime", source = "updatedTime")
    CustomFieldDefinitionDto toDto(CustomFieldDefinition entity);

    CustomFieldDefinition toEntity(CustomFieldDefinitionDto dto);

    void updateFromDto(CustomFieldDefinitionDto dto, @MappingTarget CustomFieldDefinition entity);
}

