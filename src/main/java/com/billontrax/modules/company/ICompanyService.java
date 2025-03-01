package com.billontrax.modules.company;

import com.billontrax.modules.company.modals.CompanyDetailsDto;
import com.billontrax.modules.company.modals.NewCompanyCreateRequest;

import java.math.BigInteger;

public interface ICompanyService {
    BigInteger inviteCompanyAndUser(NewCompanyCreateRequest body);

    CompanyDetailsDto fetchCompanyDetailsById(BigInteger id);
}
