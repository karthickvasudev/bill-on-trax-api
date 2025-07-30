package com.billontrax.modules.core.user.repositories;

import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("""
			select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when r.businessId = 0 then true else false end)
			from User u inner join RoleUserMap rum on rum.userId = u.id AND u.isDeleted = false
			inner join Role r on r.id =  rum.roleId
			where (u.username = :username or u.email = :username or u.phoneNumber = :username) and u.isDeleted = false
			""")
	Optional<UserProfileDto> fetchUserProfile(String username);
    @Query("""
			select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when r.businessId = 0 then true else false end)
			from User u inner join RoleUserMap rum on rum.userId = u.id AND u.isDeleted = false
			inner join Role r on r.id =  rum.roleId
			where (u.username = :username or u.email = :username or u.phoneNumber = :username) and r.businessId = :businessId and u.isDeleted = false
			""")
    Optional<UserProfileDto> fetchUserProfile(String username, Long businessId);

    @Query("""
            select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when r.businessId = 0 then true else false end)
            from User u
            inner join RoleUserMap rum on rum.userId = u.id
            inner join Role r on r.id =  rum.roleId
            where u.id = :userId and r.businessId = :businessId and u.isDeleted = false
            """)
    Optional<UserProfileDto> fetchUserProfileById(Long userId, Long businessId);
}
