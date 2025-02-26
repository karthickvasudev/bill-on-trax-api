package com.billixapp.modules.authentication;

import com.billixapp.common.dtos.ApiResponse;
import com.billixapp.common.dtos.ResponseStatus;
import com.billixapp.common.enums.ResponseCode;
import com.billixapp.modules.authentication.models.LoginRequest;
import com.billixapp.modules.authentication.models.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest body) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Login successful"));
        response.setData(authenticationService.login(body));
        return response;
    }
}
