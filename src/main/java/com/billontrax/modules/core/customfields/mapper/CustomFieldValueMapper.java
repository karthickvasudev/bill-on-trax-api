package com.billontrax.modules.core.customfields.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldValue;

@Mapper(componentModel = "spring")
public interface CustomFieldValueMapper {
    @Mapping(source = "customField.id", target = "customFieldId")
    CustomFieldValueDto toDto(CustomFieldValue entity);

    @Mapping(target = "customField", ignore = true)
    CustomFieldValue toEntity(CustomFieldValueDto dto);
}
