package com.billontrax.modules.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInformationRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
}
