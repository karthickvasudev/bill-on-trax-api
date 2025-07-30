package com.billontrax.modules.store.controllers;

import com.billontrax.modules.store.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
	private final StoreService storeService;

}