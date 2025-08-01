package com.billontrax.modules.business.repositories;

import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.dtos.BusinessListDto;
import com.billontrax.modules.business.dtos.OnboardingDetailsDto;
import com.billontrax.modules.business.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
	@Query("""
			select new com.billontrax.modules.business.dtos.BusinessDetailDto(b, u)
						from Business b join User u on b.ownerId = u.id
						where b.id = ?1
			""")
	Optional<BusinessDetailDto> fetchBusinessDetail(Long businessId);

	@Query("""
			select new com.billontrax.modules.business.dtos.OnboardingDetailsDto(b.inviteId, b, u)
						from Business b join User u on b.ownerId = u.id
						where b.inviteId = ?1 and b.status in ('INVITED', 'BUSINESS_DETAILS_UPDATED', 'OWNER_DETAILS_UPDATED')
			""")
	Optional<OnboardingDetailsDto> fetchOnboardingDetailsByInviteId(String inviteId);

	@Query("""
			select new com.billontrax.modules.business.dtos.BusinessListDto(
							b.id, b.name, b.status, b.email, b.phoneNumber, b.address, b.city, b.state, b.country,
							b.zipCode, b.logoUrl, b.createdTime, b.updatedTime, u.id, u.name, u.email, u.phoneNumber
						)
						from Business b join User u on b.ownerId = u.id
						and b.status != 'DELETED'
			""")
	List<BusinessListDto> searchBusiness();
}
