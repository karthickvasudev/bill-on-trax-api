package com.billontrax.modules.user.modals;

import com.billontrax.modules.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInformationDto {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean isPasswordResetRequired;

    public OwnerInformationDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.isPasswordResetRequired = user.getIsPasswordResetRequired();
    }
}
