package com.billixapp.modules.user;

import com.billixapp.modules.role.Role;
import com.billixapp.modules.role.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    private Boolean isPasswordResetRequired;
    private RoleName role;
    @JsonIgnore
    private Boolean isDeleted;
    private Date createdOn;
    private Date updatedOn;

    public UserProfileDto(User user, Role role) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.fullName = (Objects.requireNonNullElse(user.getFirstName(), "") + " " + Objects.requireNonNullElse(user.getLastName(), "")).trim();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.createdOn = user.getCreatedOn();
        this.updatedOn = user.getUpdatedOn();
        this.role = role.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isDeleted = user.getIsDeleted();
        this.isPasswordResetRequired = user.getIsPasswordResetRequired();
    }

    @JsonIgnore
    public UserDetails getAsUserDetails(){
        return new UserDetailsImpl(this);
    }
}
