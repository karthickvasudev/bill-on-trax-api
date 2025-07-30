package com.billontrax.modules.store.services;

import com.billontrax.modules.store.dtos.CreateStoreDto;

public interface StoreService {

	void createStore(CreateStoreDto store);
}