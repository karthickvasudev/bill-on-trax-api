package com.billontrax.modules.business;

import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.BusinessListDto;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/business")
@RequiredArgsConstructor
public class BusinessController {
    private final IBusinessService businessService;

    @PostMapping("create")
    public ApiResponse<BigInteger> createBusiness(@RequestBody NewBusinessCreateRequest body) {
        ApiResponse<BigInteger> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.CREATED, "Company created successfully."));
        response.setData(businessService.inviteBusinessAndUser(body));
        return response;
    }

    @GetMapping("{id}")
    public ApiResponse<BusinessDetailsDto> getBusinessById(@PathVariable("id") BigInteger id) {
        ApiResponse<BusinessDetailsDto> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Company retrieved successfully."));
        response.setData(businessService.fetchBusinessDetailsById(id));
        return response;
    }

    @PostMapping("list")
    public ApiResponse<List<BusinessListDto>> getAllBusiness() {
        ApiResponse<List<BusinessListDto>> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "data retrieved successfully."));
        response.setData(businessService.fetchBusinessList());
        return response;
    }
}
