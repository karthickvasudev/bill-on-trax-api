package com.billontrax.modules.core.template.entities;

import com.billontrax.common.entities.Timestamped;
import com.billontrax.modules.core.template.enums.TemplateName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Template")
@Table(name = "template")
public class Template extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Enumerated(EnumType.STRING)
	private TemplateName template;
	private String htmlContent;
	private Boolean isSuperAdmin;
	private Boolean isDeleted;
}
