package com.billixapp.modules.authentication;

import com.billixapp.modules.authentication.models.LoginRequest;
import com.billixapp.modules.authentication.models.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest body);
}
