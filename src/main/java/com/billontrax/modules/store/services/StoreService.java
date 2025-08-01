package com.billontrax.modules.store.services;

import com.billontrax.modules.store.dtos.CreateStoreDto;
import com.billontrax.modules.store.entities.Store;

public interface StoreService {

	Store createStore(CreateStoreDto store);
}