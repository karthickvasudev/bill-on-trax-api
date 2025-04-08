package com.billontrax.modules.user;

import com.billontrax.modules.user.modals.CreateUserRequest;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UpdateUserInformationRequest;
import com.billontrax.modules.user.modals.UserProfileDto;

import java.math.BigInteger;

public interface IUserService {
    UserProfileDto fetchProfile();

    void resetPassword(ResetPasswordRequest body);

    User createUser(CreateUserRequest body);

	User updateUserInformation(BigInteger userId, UpdateUserInformationRequest updateUserRequest);

	User updatePassword(BigInteger userId, ResetPasswordRequest body);
}
