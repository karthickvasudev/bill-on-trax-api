package com.billontrax.modules.onboarding.modals;

import com.billontrax.modules.business.Business;
import com.billontrax.modules.business.enums.BusinessStatus;
import com.billontrax.modules.business.modals.BusinessDetailsDto;
import com.billontrax.modules.business.modals.BusinessDto;
import com.billontrax.modules.user.User;
import com.billontrax.modules.user.modals.OwnerInformationDto;
import com.billontrax.modules.user.modals.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingDetailsDto {
    private BusinessDto businessDetails;
    private UserProfileDto userProfile;

    public OnboardingDetailsDto(Business business, UserProfileDto userProfileDto) {
        businessDetails = new BusinessDto(business);
        userProfile = userProfileDto;
    }
}
