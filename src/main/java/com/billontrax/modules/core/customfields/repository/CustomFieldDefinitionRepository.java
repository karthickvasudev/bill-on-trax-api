package com.billontrax.modules.core.customfields.repository;

import com.billontrax.modules.core.customfields.entity.CustomFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomFieldDefinitionRepository extends JpaRepository<CustomFieldDefinition, Long> {
    List<CustomFieldDefinition> findByModuleAndStoreId(String module, Long storeId);
}
