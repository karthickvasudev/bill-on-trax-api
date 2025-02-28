package com.billixapp.modules.user;

public interface UserService {
    UserProfileDto fetchProfile();

    void resetPassword(ResetPasswordRequest body);
}
