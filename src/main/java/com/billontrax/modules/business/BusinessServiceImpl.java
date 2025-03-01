package com.billontrax.modules.business;

import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.CreateBusinessRequest;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;
import com.billontrax.modules.user.IUserService;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {
    private final BusinessRepository businessRepository;
    private final IUserService userService;

    @Override
    public BigInteger inviteCompanyAndUser(NewBusinessCreateRequest body) {
        CreateBusinessRequest companyDetails = body.getCompanyDetails();
        CreateUserRequest ownerDetails = body.getOwnerDetails();
        User user = userService.createUser(ownerDetails);
        Business business = new Business();
        business.setName(companyDetails.getCompanyName());
        business.setOwnerId(user.getId());
        business.setAddress(companyDetails.getAddress());
        business.setCity(companyDetails.getCity());
        business.setState(companyDetails.getState());
        business.setZip(companyDetails.getZip());
        return businessRepository.save(business).getId();
    }

    @Override
    public BusinessDetailsDto fetchCompanyDetailsById(BigInteger id) {
        return businessRepository.fetchCompanyDetailsById(id).orElseThrow(() -> new ErrorMessageException("Company Details not found"));
    }
}
