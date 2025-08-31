package com.billontrax.modules.core.customfields.entity;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.core.customfields.enums.CustomFieldModule;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custom_field_definition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFieldDefinition extends TimestampedWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CustomFieldModule module;
    private Long businessId;
    private String fieldName;
    @Enumerated(EnumType.STRING)
    private CustomFieldType fieldType;
    private Boolean isRequired;
    private String defaultValue;
    @Column(columnDefinition = "json")
    private String additionalOptions;
}
