package com.billontrax.modules.user.modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInformationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
