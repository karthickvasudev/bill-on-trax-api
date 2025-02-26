package com.billixapp.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    @Query("select new com.billixapp.modules.user.UserProfileDto(u, r) from users u " +
            "join RoleUserMap rum on u.id = rum.userId join Roles r on rum.roleId = r.id " +
            "where u.email = :username or u.phoneNumber = :username or u.username = :username and u.isDeleted = false")
    Optional<UserProfileDto> fetchUserProfile(String username);

    @Query("select new com.billixapp.modules.user.UserProfileDto(u, r) from users u " +
            "join RoleUserMap rum on u.id = rum.userId join Roles r on rum.roleId = r.id " +
            "where u.id = :userId and u.isDeleted = false")
    Optional<UserProfileDto> fetchUserProfileById(BigInteger userId);
}
