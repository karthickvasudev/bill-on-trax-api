package com.billontrax.modules.category.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "category")
@Table(name = "category")
public class Category extends TimestampedWithUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long parentCategoryId;
	private Long businessId;
	private String name;
	private String hsnCode;
	private BigInteger gstRate;
	private Boolean isDeleted = false;
}
