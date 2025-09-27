package com.billontrax.modules.core.customfields.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldValue;

@Mapper(componentModel = "spring")
public interface CustomFieldValueMapper {

    CustomFieldValueDto toDto(CustomFieldValue entity);

    CustomFieldValue toEntity(CustomFieldValueDto dto);
}
