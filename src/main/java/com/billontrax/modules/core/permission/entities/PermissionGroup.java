package com.billontrax.modules.core.permission.entities;

import com.billontrax.common.entities.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PermissionGroup")
@Table(name = "permission_group")
public class PermissionGroup extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Boolean isDefault;
	private Boolean isDeleted;
	private Date createdTime;
	private Date updatedTime;
}
