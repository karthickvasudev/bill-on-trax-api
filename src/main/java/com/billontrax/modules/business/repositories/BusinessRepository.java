package com.billontrax.modules.business.repositories;

import com.billontrax.modules.business.dtos.BusinessDetailDto;
import com.billontrax.modules.business.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusinessRepository extends JpaRepository<Business, Long> {
	@Query("""
			select new com.billontrax.modules.business.dtos.BusinessDetailDto(b, u)
						from Business b join User u on b.ownerId = u.id 
						where b.id = ?1
			""")
	BusinessDetailDto fetchBusinessDetail(Long businessId);
}
