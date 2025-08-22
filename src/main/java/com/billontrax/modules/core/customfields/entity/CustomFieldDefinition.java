package com.billontrax.modules.core.customfields.entity;

import com.billontrax.common.entities.TimestampedWithUser;
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

    private String module;
    private Long storeId;
    private String fieldName;

    @Enumerated(EnumType.STRING)
    private CustomFieldType fieldType;

    private Boolean isRequired;
    private String defaultValue;

    @Column(columnDefinition = "json")
    private String options;
}
