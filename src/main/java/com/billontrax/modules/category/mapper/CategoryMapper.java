package com.billontrax.modules.category.mapper;

import com.billontrax.modules.category.dtos.CreateCategory;
import com.billontrax.modules.category.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	Category toEntity(CreateCategory createCategory);
}
