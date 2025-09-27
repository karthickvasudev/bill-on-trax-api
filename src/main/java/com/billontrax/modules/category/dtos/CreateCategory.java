package com.billontrax.modules.category.dtos;

import lombok.Data;

@Data
public class CreateCategory {
	private Long parentCategoryId;
	private Long businessId;
	private String name;
	private String hsnCode;
	private Long gstRate;
}
