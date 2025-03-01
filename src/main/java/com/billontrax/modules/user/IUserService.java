package com.billontrax.modules.user;

import com.billontrax.modules.user.modals.CreateUserRequest;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UserProfileDto;

public interface IUserService {
    UserProfileDto fetchProfile();

    void resetPassword(ResetPasswordRequest body);

    User createUser(CreateUserRequest body);
}
