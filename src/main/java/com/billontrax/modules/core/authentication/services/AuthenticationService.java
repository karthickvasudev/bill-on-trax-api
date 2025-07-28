package com.billontrax.modules.core.authentication.services;

import com.billontrax.modules.core.authentication.dto.LoginRequest;
import com.billontrax.modules.core.authentication.dto.LoginResponse;

public interface AuthenticationService {
	LoginResponse login(LoginRequest body);
}
