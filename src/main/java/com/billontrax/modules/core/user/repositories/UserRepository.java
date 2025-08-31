package com.billontrax.modules.core.user.repositories;

import com.billontrax.modules.core.user.dto.UserProfileDto;
import com.billontrax.modules.core.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("""
			select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when u.businessId = 0 then true else false end)
			from User u inner join RoleUserMap rum on rum.userId = u.id AND u.isDeleted = false
			inner join Role r on r.id =  rum.roleId
			left join Business b on b.id = u.businessId or u.businessId = 0
			where (u.username = :username or u.email = :username or u.phoneNumber = :username) and u.isDeleted = false
			and (b.status = 'ACTIVE' or u.businessId = 0)
			""")
	Optional<UserProfileDto> fetchUserProfile(String username);

	@Query("""
			select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when u.businessId = 0 then true else false end)
			from User u inner join RoleUserMap rum on rum.userId = u.id AND u.isDeleted = false
			inner join Role r on r.id =  rum.roleId
			left join Business b on b.id = u.businessId or u.businessId = 0
			where (u.username = :username or u.email = :username or u.phoneNumber = :username) and r.businessId = :businessId
						and u.isDeleted = false and (b.status = 'ACTIVE' or u.businessId = 0)
			""")
	Optional<UserProfileDto> fetchUserProfile(String username, Long businessId);

	@Query("""
			 select new com.billontrax.modules.core.user.dto.UserProfileDto(u, r, case when u.businessId = 0 then true else false end)
			 from User u
			 inner join RoleUserMap rum on rum.userId = u.id
			 inner join Role r on r.id =  rum.roleId
			 left join Business b on b.id = u.businessId or u.businessId = 0
			 where u.id = :userId and r.businessId = :businessId and u.isDeleted = false
			  and (b.status = 'ACTIVE' or u.businessId = 0)
			""")
	Optional<UserProfileDto> fetchUserProfileById(Long userId, Long businessId);

	Long countByUsername(String username);
}
