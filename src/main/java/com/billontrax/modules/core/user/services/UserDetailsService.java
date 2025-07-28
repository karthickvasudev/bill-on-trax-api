package com.billontrax.modules.core.user.services;

import com.billontrax.modules.core.permission.dto.PermissionDto;
import com.billontrax.modules.core.permission.dto.UserPermissionDto;
import com.billontrax.modules.core.user.dto.UserProfileDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsService implements UserDetails {

	private final UserProfileDto userProfile;

	public UserDetailsService(UserProfileDto userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (UserPermissionDto userPermission : userProfile.getUserPermissions()) {
            for (PermissionDto permission : userPermission.getPermissions()) {
                list.add(new SimpleGrantedAuthority(permission.getPermissionCode().toLowerCase()));
            }
        }
		return list;
	}

	@Override
	public String getPassword() {
		return userProfile.getPassword();
	}

	@Override
	public String getUsername() {
		return userProfile.getUsername();
	}

	@Override
	public boolean isEnabled() {
		return !userProfile.getIsDeleted();
	}
}
