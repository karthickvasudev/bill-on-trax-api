package com.billontrax.modules.company;

import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.company.modals.CompanyDetailsDto;
import com.billontrax.modules.company.modals.CreateCompanyRequest;
import com.billontrax.modules.company.modals.NewCompanyCreateRequest;
import com.billontrax.modules.user.IUserService;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final IUserService userService;

    @Override
    public BigInteger inviteCompanyAndUser(NewCompanyCreateRequest body) {
        CreateCompanyRequest companyDetails = body.getCompanyDetails();
        CreateUserRequest ownerDetails = body.getOwnerDetails();
        User user = userService.createUser(ownerDetails);
        Company company = new Company();
        company.setName(companyDetails.getCompanyName());
        company.setOwnerId(user.getId());
        company.setAddress(companyDetails.getAddress());
        company.setCity(companyDetails.getCity());
        company.setState(companyDetails.getState());
        company.setZip(companyDetails.getZip());
        return companyRepository.save(company).getId();
    }

    @Override
    public CompanyDetailsDto fetchCompanyDetailsById(BigInteger id) {
        return companyRepository.fetchCompanyDetailsById(id).orElseThrow(() -> new ErrorMessageException("Company Details not found"));
    }
}
