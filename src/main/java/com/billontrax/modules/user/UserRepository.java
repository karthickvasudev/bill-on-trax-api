package com.billontrax.modules.user;

import com.billontrax.modules.user.modals.UserProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    @Query("select new com.billontrax.modules.user.modals.UserProfileDto(u, r) from Users u " +
            "join RoleUserMap rum on u.id = rum.userId join Roles r on rum.roleId = r.id " +
            "where u.email = :username or u.phoneNumber = :username or u.username = :username and u.isDeleted = false")
    Optional<UserProfileDto> fetchUserProfile(String username);

    @Query("select new com.billontrax.modules.user.modals.UserProfileDto(u, r) from Users u " +
            "join RoleUserMap rum on u.id = rum.userId join Roles r on rum.roleId = r.id " +
            "where u.id = :userId and u.isDeleted = false")
    Optional<UserProfileDto> fetchUserProfileById(BigInteger userId);
}
