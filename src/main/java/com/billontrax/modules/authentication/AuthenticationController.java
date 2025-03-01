package com.billontrax.modules.authentication;

import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.authentication.modals.LoginRequest;
import com.billontrax.modules.authentication.modals.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest body) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Login successful"));
        response.setData(authenticationService.login(body));
        return response;
    }
}
