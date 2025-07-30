package com.billontrax.modules.core.user.dto;

import com.billontrax.modules.core.permission.dto.UserPermissionDto;
import com.billontrax.modules.core.role.entities.Role;
import com.billontrax.modules.core.user.entities.User;
import com.billontrax.modules.core.user.services.UserDetailsService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Long id;
    private Long businessId;
    private String name;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Boolean isSuperAdmin;
    private Boolean isPasswordResetRequired;
    private String role;
    private List<UserPermissionDto> userPermissions;
    @JsonIgnore
    private Boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    public UserProfileDto(User user, Role role, Boolean isSuperAdmin) {
        this.id = user.getId();
        this.businessId = user.getBusinessId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.createdTime = user.getCreatedTime();
        this.updatedTime = user.getUpdatedTime();
        this.role = role.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isDeleted = user.getIsDeleted();
        this.isPasswordResetRequired = user.getIsPasswordResetRequired();
        this.isSuperAdmin = isSuperAdmin;
    }

    @JsonIgnore
    public UserDetails getAsUserDetails(){
        return new UserDetailsService(this);
    }
}
