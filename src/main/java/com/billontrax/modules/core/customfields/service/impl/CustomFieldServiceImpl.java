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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomFieldServiceImpl implements CustomFieldService {

	private final CustomFieldDefinitionRepository customFieldDefinitionRepo;
	private final CustomFieldValueRepository customerFieldValueRepo;
	private final CustomFieldDefinitionMapper definitionMapper;
	private final CustomFieldValueMapper valueMapper;
	private final FeaturesRepository featureRepository;

	@Override
	public CustomFieldDefinitionDto createDefinition(CustomFieldDefinitionDto dto) {
		CustomFieldDefinition entity = definitionMapper.toEntity(dto);
		entity.setBusinessId(CurrentUserHolder.getBusinessId());
		entity.setIsDeleted(false);
		return definitionMapper.toDto(customFieldDefinitionRepo.save(entity));
	}

	@Override
	public CustomFieldDefinitionDto updateDefinition(Long id, CustomFieldDefinitionDto dto) {
		CustomFieldDefinition entity = customFieldDefinitionRepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Custom field not found"));
		entity.setBusinessId(CurrentUserHolder.getBusinessId());
		definitionMapper.updateFromDto(dto, entity);
		entity.setIsDeleted(false);
		return definitionMapper.toDto(customFieldDefinitionRepo.save(entity));
	}

	@Override
	public void deleteDefinition(Long id) {
		customFieldDefinitionRepo.findById(id).ifPresent(definition -> {
			definition.setIsDeleted(true);
			customFieldDefinitionRepo.save(definition);
		});
	}

	@Override
	public List<CustomFieldDefinitionDto> listDefinitions(CustomFieldModule module) {
		return customFieldDefinitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module, CurrentUserHolder.getBusinessId())
				.stream().map(definitionMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public void saveFieldValues(CustomFieldModule module, Long recordId, List<CustomFieldValueDto> values) {
		Map<Long, CustomFieldDefinition> map = customFieldDefinitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module,
						CurrentUserHolder.getBusinessId()).stream()
				.collect(Collectors.toMap(CustomFieldDefinition::getId, Function.identity()));
		for (CustomFieldValueDto value : values) {
			CustomFieldDefinition customFieldDefinition = map.get(value.getId());
			if (customFieldDefinition == null) {
				throw new ErrorMessageException("Custom field definition not found");
			}
			if (customFieldDefinition.getIsRequired() && (value.getValue() == null || value.getValue().toString()
					.isBlank())) {
				throw new ValidationException("Field " + customFieldDefinition.getFieldName() + " is required");
			}
			CustomFieldValue valueEntity = valueMapper.toEntity(value);
			valueEntity.setCustomFieldId(customFieldDefinition.getId());
			valueEntity.setRecordId(recordId);
			switch (value.getType()) {
			case TEXT, NUMBER, DROPDOWN, CHOICE -> valueEntity.setValue(String.valueOf(value.getValue()));
			case DATE, DATETIME -> valueEntity.setValue(((Date) value.getValue()).toInstant().toString());
			case MULTICHOICE -> valueEntity.setValue(value.getValue().toString());
			}
			customerFieldValueRepo.save(valueEntity);
		}
	}

	@Override
	public List<CustomFieldValueDto> getFieldValues(CustomFieldModule module, Long storeId, Long recordId) {
		List<CustomFieldDefinition> definitions = customFieldDefinitionRepo.findAllByModuleAndBusinessIdAndIsDeletedFalse(module,
				storeId);
		List<Long> fieldIds = definitions.stream().map(CustomFieldDefinition::getId).toList();
		List<CustomFieldValue> values = customerFieldValueRepo.findByRecordId(recordId).stream()
				.filter(v -> fieldIds.contains(v.getCustomFieldId())).toList();
		return values.stream().map(valueMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public List<Features> getCustomFieldSupportedModules() {
		return featureRepository.findAllByIsCustomFieldSupportTrue();
	}

	@Override
	public CustomFieldDefinitionDto getDefinitionById(Long id) {
		CustomFieldDefinition cf = customFieldDefinitionRepo.findByIdAndBusinessIdAndIsDeletedFalse(id,
						CurrentUserHolder.getBusinessId())
				.orElseThrow(() -> new ErrorMessageException("Custom field definition not found"));
		return definitionMapper.toDto(cf);
	}
}
