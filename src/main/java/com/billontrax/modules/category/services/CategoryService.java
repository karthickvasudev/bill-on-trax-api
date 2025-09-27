package com.billontrax.modules.category.services;

import com.billontrax.modules.category.dtos.CreateCategory;
import com.billontrax.modules.category.entities.Category;
import com.billontrax.modules.category.mapper.CategoryMapper;
import com.billontrax.modules.category.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
	private final CategoryMapper mapper;
	private final CategoryRepository repository;

	public Category createCategory(CreateCategory body) {
		log.debug("Creating category with data: {}", body);
		Category category = mapper.toEntity(body);
		return repository.save(category);
	}
}
