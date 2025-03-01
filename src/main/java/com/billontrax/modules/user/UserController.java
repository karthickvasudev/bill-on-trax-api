package com.billontrax.modules.user;

import com.billontrax.common.models.ApiResponse;
import com.billontrax.common.models.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.user.modals.ResetPasswordRequest;
import com.billontrax.modules.user.modals.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

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
