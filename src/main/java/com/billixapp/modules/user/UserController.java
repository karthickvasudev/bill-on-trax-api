package com.billixapp.modules.user;

import com.billixapp.common.dtos.ApiResponse;
import com.billixapp.common.dtos.ResponseStatus;
import com.billixapp.common.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("profile")
    public ApiResponse<UserProfileDto> getProfile() {
        ApiResponse<UserProfileDto> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK));
        response.setData(userService.fetchProfile());
        return response;
    }

    @PostMapping("reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody ResetPasswordRequest body){
        ApiResponse<Void> response = new ApiResponse<>();
        response.setStatus(new ResponseStatus(ResponseCode.OK, "Password reset successfully. Please log in."));
        userService.resetPassword(body);
        return response;
    }
}
