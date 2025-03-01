package com.billontrax.modules.authentication;

import com.billontrax.modules.authentication.modals.LoginRequest;
import com.billontrax.modules.authentication.modals.LoginResponse;

public interface IAuthenticationService {
    LoginResponse login(LoginRequest body);
}
