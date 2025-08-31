package com.billontrax.modules.core.authentication.controllers;

import com.billontrax.modules.core.authentication.dto.LoginRequest;
import com.billontrax.modules.core.authentication.dto.LoginResponse;
import com.billontrax.modules.core.authentication.services.AuthenticationService;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
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
    public Response<LoginResponse> login(@RequestBody LoginRequest body) {
        Response<LoginResponse> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Login successful"));
        response.setData(authenticationService.login(body));
        return response;
    }
}
