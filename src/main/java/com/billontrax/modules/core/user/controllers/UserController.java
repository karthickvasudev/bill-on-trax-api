package com.billontrax.modules.core.user.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.core.user.dto.ResetPasswordRequest;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("profile")
    public Response<UserProfileDto> getProfile() {
        Response<UserProfileDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        response.setData(userService.fetchProfile());
        return response;
    }

    @PutMapping("reset-password")
    public Response<Void> resetPassword(@RequestBody ResetPasswordRequest body){
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Password reset successfully. Please log in."));
        userService.resetPassword(body);
        return response;
    }
}
