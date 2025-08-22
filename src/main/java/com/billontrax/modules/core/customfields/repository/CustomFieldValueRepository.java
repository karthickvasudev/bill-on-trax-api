package com.billontrax.modules.core.customfields.repository;

import com.billontrax.modules.core.customfields.entity.CustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomFieldValueRepository extends JpaRepository<CustomFieldValue, Long> {
    List<CustomFieldValue> findByCustomFieldIdAndRecordId(Long customFieldId, Long recordId);

    List<CustomFieldValue> findByRecordIdAndCustomField_ModuleAndCustomField_StoreId(
            Long recordId, String module, Long storeId);

    List<CustomFieldValue> findByRecordId(Long recordId);
}
