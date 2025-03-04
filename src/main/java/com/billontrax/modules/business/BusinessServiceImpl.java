package com.billontrax.modules.business;

import com.billontrax.exceptions.ErrorMessageException;
import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.BusinessListDto;
import com.billontrax.modules.business.modals.CreateBusinessRequest;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;
import com.billontrax.modules.user.IUserService;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {
    private final BusinessRepository businessRepository;
    private final IUserService userService;

    @Override
    public BigInteger inviteBusinessAndUser(NewBusinessCreateRequest body) {
        CreateBusinessRequest companyDetails = body.getCompanyDetails();
        CreateUserRequest ownerDetails = body.getOwnerDetails();
        User user = userService.createUser(ownerDetails);
        Business business = new Business();
        business.setName(companyDetails.getBusinessName());
        business.setOwnerId(user.getId());
        business.setAddress(companyDetails.getAddress());
        business.setCity(companyDetails.getCity());
        business.setState(companyDetails.getState());
        business.setZipcode(companyDetails.getZipcode());
        return businessRepository.save(business).getId();
    }

    @Override
    public BusinessDetailsDto fetchBusinessDetailsById(BigInteger id) {
        return businessRepository.fetchBusinessDetailsById(id).orElseThrow(() -> new ErrorMessageException("Company Details not found"));
    }

    @Override
    public List<BusinessListDto> fetchBusinessList() {
        return businessRepository.fetchBusinessList();
    }
}
