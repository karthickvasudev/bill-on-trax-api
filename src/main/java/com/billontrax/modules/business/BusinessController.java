package com.billontrax.modules.business;

import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class BusinessController {
    private final IBusinessService companyService;

    @PostMapping("create")
    public ApiResponse<BigInteger> createCompany(@RequestBody NewBusinessCreateRequest body) {
        ApiResponse<BigInteger> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.CREATED, "Company created successfully."));
        response.setData(companyService.inviteBusinessAndUser(body));
        return response;
    }

    @GetMapping("{id}")
    public ApiResponse<BusinessDetailsDto> getCompanyById(@PathVariable("id") BigInteger id) {
        ApiResponse<BusinessDetailsDto> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Company retrieved successfully."));
        response.setData(companyService.fetchBusinessDetailsById(id));
        return response;
    }
}
