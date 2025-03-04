package com.billontrax.modules.business;

import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.BusinessListDto;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;

import java.math.BigInteger;
import java.util.List;

public interface IBusinessService {
    BigInteger inviteBusinessAndUser(NewBusinessCreateRequest body);

    BusinessDetailsDto fetchBusinessDetailsById(BigInteger id);

    List<BusinessListDto> fetchBusinessList();

    void sendInvite(BigInteger id);
}
