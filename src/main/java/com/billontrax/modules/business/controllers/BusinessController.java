package com.billontrax.modules.business.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.dtos.BusinessListDto;
import com.billontrax.modules.business.dtos.CreateBusinessRequest;
import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.business.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/business")
@RequiredArgsConstructor
public class BusinessController {
	private final BusinessService businessService;

	@PostMapping
	public Response<Business> createBusiness(@RequestBody CreateBusinessRequest body) {
		Response<Business> response = new Response<>(ResponseStatus.of(ResponseCode.CREATED));
		response.setData(businessService.createBusiness(body));
		return response;
	}

	@PutMapping("{businessId}/invite")
	public Response<Void> inviteBusiness(@PathVariable Long businessId){
		Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Invite sent successfully"));
		businessService.sendBusinessInvite(businessId);
		return response;
	}

	@GetMapping("{businessId}")
	public Response<BusinessDetailDto> getBusiness(@PathVariable Long businessId){
		Response<BusinessDetailDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
		response.setData(businessService.getBusinessDetailsById(businessId));
		return response;
	}

	@GetMapping("search")
	public Response<List<BusinessListDto>> searchBusiness() {
		Response<List<BusinessListDto>> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
		response.setData(businessService.searchBusiness());
		return response;
	}
}
