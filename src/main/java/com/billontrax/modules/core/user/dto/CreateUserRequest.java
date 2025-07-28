package com.billontrax.modules.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private Long businessId;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
