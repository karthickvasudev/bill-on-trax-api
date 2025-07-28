package com.billontrax.modules.business.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.dtos.CreateBusinessRequest;
import com.billontrax.modules.business.entities.Business;
import com.billontrax.modules.business.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/business")
@RequiredArgsConstructor
public class BusinessController {
	private final BusinessService businessService;

	@PostMapping("create")
	public Response<Business> createBusiness(@RequestBody CreateBusinessRequest body) {
		Response<Business> response = new Response<>();
		response.setStatus(new ResponseStatus(ResponseCode.CREATED));
		response.setData(businessService.createBusiness(body));
		return response;
	}

	@PutMapping("{businessId}/invite")
	public Response<Void> inviteBusiness(@PathVariable Long businessId){
		Response<Void> response = new Response<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK_NOTIFY, "Invite sent successfully"));
		businessService.sendBusinessInvite(businessId);
		return response;
	}

	@GetMapping("{businessId}")
	public Response<BusinessDetailDto> getBusiness(@PathVariable Long businessId){
		Response<BusinessDetailDto> response = new Response<>();
		response.setStatus(new ResponseStatus(ResponseCode.OK));
		response.setData(businessService.getBusinessDetailsById(businessId));
		return response;
	}
}
