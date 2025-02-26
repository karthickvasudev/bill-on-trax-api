package com.billixapp.modules.user;

import com.billixapp.common.dtos.ApiResponse;
import com.billixapp.common.dtos.ResponseStatus;
import com.billixapp.common.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
