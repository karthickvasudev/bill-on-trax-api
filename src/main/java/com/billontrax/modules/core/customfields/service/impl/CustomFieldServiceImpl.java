package com.billontrax.modules.core.customfields.service.impl;

import com.billontrax.common.config.CurrentUserHolder;
import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.core.customfields.dto.CustomFieldDefinitionDto;
import com.billontrax.modules.core.customfields.dto.CustomFieldValueDto;
import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import com.billontrax.modules.core.customfields.entity.CustomFieldType;
import com.billontrax.modules.core.customfields.entity.CustomFieldValue;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;
import com.billontrax.modules.core.customfields.mapper.CustomFieldDefinitionMapper;
import com.billontrax.modules.core.customfields.mapper.CustomFieldValueMapper;
import com.billontrax.modules.core.customfields.repository.CustomFieldDefinitionRepository;
import com.billontrax.modules.core.customfields.repository.CustomFieldValueRepository;
import com.billontrax.modules.core.customfields.service.CustomFieldService;
import com.billontrax.modules.core.features.entities.Features;
import com.billontrax.modules.core.features.repository.FeaturesRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomFieldServiceImpl implements CustomFieldService {

    private final CustomFieldDefinitionRepository definitionRepo;
    private final CustomFieldValueRepository valueRepo;
    private final CustomFieldDefinitionMapper definitionMapper;
    private final CustomFieldValueMapper valueMapper;
    private final FeaturesRepository featureRepository;

    @Override
    public CustomFieldDefinitionDto createDefinition(CustomFieldDefinitionDto dto) {
        CustomFieldDefinition entity = definitionMapper.toEntity(dto);
        entity.setBusinessId(CurrentUserHolder.getBusinessId());
        entity.setIsDeleted(false);
        return definitionMapper.toDto(definitionRepo.save(entity));
    }

    @Override
    public CustomFieldDefinitionDto updateDefinition(Long id, CustomFieldDefinitionDto dto) {
        CustomFieldDefinition entity = definitionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Custom field not found"));
        entity.setBusinessId(CurrentUserHolder.getBusinessId());
        definitionMapper.updateFromDto(dto, entity);
        entity.setIsDeleted(false);
        return definitionMapper.toDto(definitionRepo.save(entity));
    }

    @Override
    public void deleteDefinition(Long id) {
        definitionRepo.findById(id).ifPresent(definition -> {
            definition.setIsDeleted(true);
            definitionRepo.save(definition);
        });
    }

    @Override
    public List<CustomFieldDefinitionDto> listDefinitions(CustomFieldModule module) {
        return definitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module, CurrentUserHolder.getBusinessId())
                .stream().map(definitionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void saveFieldValues(CustomFieldModule module, Long recordId, Map<String, Object> values) {
        List<CustomFieldDefinition> customFields = definitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(
                module, CurrentUserHolder.getBusinessId());
        Set<Map.Entry<String, Object>> entries = values.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Optional<CustomFieldDefinition> matchingField = customFields.stream()
                    .filter(cf -> cf.getFieldName().equals(entry.getKey())).findFirst();
            if (matchingField.isPresent()) {
                CustomFieldDefinition def = matchingField.get();
                String value = validateValueTypeGet(def.getFieldType(), entry.getValue());
                CustomFieldValue fieldToSave = CustomFieldValue.builder().customField(def).recordId(recordId)
                        .value(value).build();
                valueRepo.save(fieldToSave);
            } else {
                throw new ValidationException("Invalid field name: " + entry.getKey());
            }
        }
    }

    @Override
    @Transactional
    public void saveFieldValues(CustomFieldModule module, Long recordId, List<CustomFieldValueDto> values) {
        List<CustomFieldDefinition> definitions = definitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module,
                CurrentUserHolder.getBusinessId());
        Map<Long, CustomFieldDefinition> defMap = definitions.stream()
                .collect(Collectors.toMap(CustomFieldDefinition::getId, d -> d));

        // Validate required fields and types
        Set<Long> providedFieldIds = values.stream().map(CustomFieldValueDto::getCustomFieldId)
                .collect(Collectors.toSet());
        for (CustomFieldDefinition def : definitions) {
            if (Boolean.TRUE.equals(def.getIsRequired()) && values.stream()
                    .noneMatch(v -> v.getCustomFieldId().equals(def.getId()))) {
                throw new ValidationException("Required field missing: " + def.getFieldName());
            }
        }

        for (CustomFieldValueDto valueDto : values) {
            CustomFieldDefinition def = defMap.get(valueDto.getCustomFieldId());
            if (def == null)
                throw new ValidationException("Invalid field id: " + valueDto.getCustomFieldId());
            validateValueTypeGet(def.getFieldType(), valueDto.getValue());
            CustomFieldValue value = valueMapper.toEntity(valueDto);
            value.setCustomField(def);
            valueRepo.save(value);
        }

        // Populate missing fields with default values
        for (CustomFieldDefinition def : definitions) {
            // if (!providedFieldIds.contains(def.getId()) && def.getDefaultValue() != null) {
            //     CustomFieldValue value = CustomFieldValue.builder().customField(def).recordId(recordId)
            //             .value(def.getDefaultValue()).build();
            //     valueRepo.save(value);
            // }
        }
    }

    @Override
    public List<CustomFieldValueDto> getFieldValues(CustomFieldModule module, Long storeId, Long recordId) {
        List<CustomFieldDefinition> definitions = definitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module,
                storeId);
        List<Long> fieldIds = definitions.stream().map(CustomFieldDefinition::getId).toList();
        List<CustomFieldValue> values = valueRepo.findByRecordId(recordId).stream()
                .filter(v -> fieldIds.contains(v.getCustomField().getId())).toList();
        return values.stream().map(valueMapper::toDto).collect(Collectors.toList());
    }

    private String validateValueTypeGet(CustomFieldType type, Object value) {
        if (value == null)
            return null;
        try {
            switch (type) {
            case NUMBER:
                if (value instanceof Number) {
                    return String.valueOf(value);
                }
                if (value instanceof CharSequence) {
                    String s = value.toString().trim();
                    if (s.isEmpty())
                        throw new ValidationException("Invalid number: empty string");
                    try {
                        // accept integer or decimal
                        new java.math.BigDecimal(s);
                        return s;
                    } catch (NumberFormatException nfe) {
                        throw new ValidationException("Invalid value for type " + type + ": " + value);
                    }
                }
                throw new ValidationException("Invalid value for type " + type + ": " + value);
            case DATE:
            case DATETIME:
                // Accept java.time types, java.util.Date or ISO strings
                if (value instanceof Temporal) {
                    return value.toString();
                }
                if (value instanceof Date) {
                    return ((Date) value).toInstant().toString();
                }
                throw new ValidationException("Invalid value for type " + type + ": " + value);
            case TEXT:
            case DROPDOWN:
            case CHOICE:
                return value.toString();
            case MULTICHOICE:
                if (value instanceof Collection) {
                    return ((Collection<?>) value).stream().map(Objects::toString).collect(Collectors.joining(","));
                }
                if (value.getClass().isArray()) {
                    int len = Array.getLength(value);
                    StringJoiner sj = new StringJoiner(",");
                    for (int i = 0; i < len; i++) {
                        Object elem = Array.get(value, i);
                        sj.add(elem == null ? "null" : elem.toString());
                    }
                    return sj.toString();
                }
                return value.toString();
            }
        } catch (Exception e) {
            throw new ValidationException("Invalid value for type " + type + ": " + value);
        }
        return null;
    }

    @Override
    public List<Features> getCustomFieldSupportedModules() {
        return featureRepository.findAllByIsCustomFieldSupportTrue();
    }

    @Override
    public CustomFieldDefinitionDto getDefinitionById(Long id) {
        CustomFieldDefinition cf = definitionRepo.findByIdAndBusinessIdAndIsDeletedFalse(id, CurrentUserHolder.getBusinessId())
                .orElseThrow(() -> new ErrorMessageException("Custom field definition not found"));
        return definitionMapper.toDto(cf);
    }
}
