package com.billontrax.modules.company.modals;

import com.billontrax.modules.user.modals.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompanyCreateRequest {
    private CreateCompanyRequest companyDetails;
    private CreateUserRequest ownerDetails;
}
