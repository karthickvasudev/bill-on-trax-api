package com.billontrax.modules.category.controller;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.category.dtos.CreateCategory;
import com.billontrax.modules.category.entities.Category;
import com.billontrax.modules.category.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService service;

	@PostMapping("/create")
	public Response<Category> createCategory(@RequestBody CreateCategory body) {
		ResponseStatus status = ResponseStatus.of(ResponseCode.CREATED, "Category created successfully");
		Response<Category> response = new Response<>(status);
		response.setData(service.createCategory(body));
		return response;
	}
}
