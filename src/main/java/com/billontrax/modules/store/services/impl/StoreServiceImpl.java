package com.billontrax.modules.store.services.impl;

import com.billontrax.common.config.CurrentUserHolder;
import com.billontrax.modules.store.dtos.CreateStoreDto;
import com.billontrax.modules.store.entities.Store;
import com.billontrax.modules.store.repositories.StoreRepository;
import com.billontrax.modules.store.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;

	@Override
	public void createStore(CreateStoreDto createStore) {
		Store store = new Store();
		store.setBusinessId(createStore.getBusinessId());
		store.setName(createStore.getName());
		store.setEmail(createStore.getEmail());
		store.setPhoneNumber(createStore.getPhoneNumber());
		store.setAddress(createStore.getAddress());
		store.setCity(createStore.getCity());
		store.setState(createStore.getState());
		store.setCountry(createStore.getCountry());
		store.setZipCode(createStore.getZipCode());

	}
}

