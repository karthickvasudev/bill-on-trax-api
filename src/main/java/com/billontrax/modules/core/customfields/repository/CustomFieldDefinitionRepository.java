package com.billontrax.modules.core.customfields.repository;

import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomFieldDefinitionRepository extends JpaRepository<CustomFieldDefinition, Long> {
    List<CustomFieldDefinition> findAllByModuleAndBusinessIdAndIsDeletedFalse(CustomFieldModule module, Long businessId);


    Optional<CustomFieldDefinition> findByIdAndBusinessIdAndIsDeletedFalse(Long id, Long businessId);
}
