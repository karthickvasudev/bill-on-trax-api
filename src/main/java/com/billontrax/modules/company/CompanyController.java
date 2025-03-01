package com.billontrax.modules.company;

import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.modules.company.modals.CompanyDetailsDto;
import com.billontrax.modules.company.modals.NewCompanyCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyService;

    @PostMapping("create")
    public ApiResponse<BigInteger> createCompany(@RequestBody NewCompanyCreateRequest body) {
        ApiResponse<BigInteger> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.CREATED, "Company created successfully."));
        response.setData(companyService.inviteCompanyAndUser(body));
        return response;
    }

    @GetMapping("{id}")
    public ApiResponse<CompanyDetailsDto> getCompanyById(@PathVariable("id") BigInteger id) {
        ApiResponse<CompanyDetailsDto> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Company retrieved successfully."));
        response.setData(companyService.fetchCompanyDetailsById(id));
        return response;
    }
}
