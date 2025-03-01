package com.billontrax.modules.business;

import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.NewBusinessCreateRequest;

import java.math.BigInteger;

public interface IBusinessService {
    BigInteger inviteBusinessAndUser(NewBusinessCreateRequest body);

    BusinessDetailsDto fetchBusinessDetailsById(BigInteger id);
}
