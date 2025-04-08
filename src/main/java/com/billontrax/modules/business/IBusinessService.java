package com.billontrax.modules.business;

import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.modals.*;

import java.math.BigInteger;
import java.util.List;

public interface IBusinessService {
    BigInteger inviteBusinessAndUser(NewBusinessCreateRequest body);

    BusinessDetailsDto fetchBusinessDetailsById(BigInteger id);

    List<BusinessListDto> fetchBusinessList();

    void sendInvite(BigInteger id);

	BusinessDto updateBusinessInformation(BigInteger businessId, UpdateBusinessRequest updateBusinessRequest);

	Business updateStatus(BigInteger businessId, BusinessStatus businessStatus);
}
