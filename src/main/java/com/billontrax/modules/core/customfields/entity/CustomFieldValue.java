package com.billontrax.modules.core.customfields.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custom_field_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomFieldValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "custom_field_id")
	private Long customFieldId;
	private Long recordId;
	private Object value;
}
