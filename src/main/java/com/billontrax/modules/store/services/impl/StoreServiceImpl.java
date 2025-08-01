package com.billontrax.modules.store.services.impl;

import com.billontrax.common.services.FileUploadService;
import com.billontrax.modules.store.dtos.CreateStoreDto;
import com.billontrax.modules.store.entities.Store;
import com.billontrax.modules.store.repositories.StoreRepository;
import com.billontrax.modules.store.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final FileUploadService fileUploadService;

	@Override
	public Store createStore(CreateStoreDto createStore) {
		Store store = new Store();
		store.setBusinessId(createStore.getBusinessId());
		store.setName(createStore.getName());
		store.setAddress(createStore.getAddress());
		store.setCity(createStore.getCity());
		store.setState(createStore.getState());
		store.setCountry(createStore.getCountry());
		store.setZipCode(createStore.getZipCode());
		if (createStore.getUserId() != null) {
			store.setCreatedBy(createStore.getUserId());
		}
		store = storeRepository.saveAndFlush(store);
		if (Objects.nonNull(createStore.getLogo())) {
			String url = fileUploadService.uploadFile(createStore.getBusinessId(),
					"stores/%s/logo".formatted(store.getId()), createStore.getLogo());
			store.setLogoUrl(url);
		}
		return storeRepository.saveAndFlush(store);
	}
}

