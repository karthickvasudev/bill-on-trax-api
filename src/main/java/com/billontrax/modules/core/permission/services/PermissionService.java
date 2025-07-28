package com.billontrax.modules.core.permission.services;

import com.billontrax.modules.core.permission.dto.PermissionDto;
import com.billontrax.modules.core.permission.dto.PermissionFlatFetchDto;
import com.billontrax.modules.core.permission.dto.UserPermissionDto;
import com.billontrax.modules.core.permission.repositories.PermissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final PermissionsRepository permissionsRepository;

	public List<UserPermissionDto> getPermissions(Long userId) {
		List<PermissionFlatFetchDto> permissionFlatFetchDtos = permissionsRepository.fetchPermissions(userId);
		Map<FeatureNameCodeRec, List<PermissionFlatFetchDto>> groupedMap = permissionFlatFetchDtos.stream().collect(
				Collectors.groupingBy(entry -> new FeatureNameCodeRec(entry.getFeatureName(), entry.getFeatureCode())));

		return groupedMap.entrySet().stream().map(entry -> {
			FeatureNameCodeRec featureNameCodeRec = entry.getKey();
			List<PermissionFlatFetchDto> list = entry.getValue();
			return new UserPermissionDto(featureNameCodeRec.featureName(), featureNameCodeRec.featureCode(),
					list.stream().map(flat -> new PermissionDto(flat.getPermissionName(), flat.getPermissionCode()))
							.toList());
		}).toList();
	}

	private record FeatureNameCodeRec(String featureName, String featureCode) {
	}
}
