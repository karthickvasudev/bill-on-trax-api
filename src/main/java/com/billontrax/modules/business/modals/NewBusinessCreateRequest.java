package com.billontrax.modules.business.modals;

import com.billontrax.modules.user.modals.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBusinessCreateRequest {
    private CreateBusinessRequest companyDetails;
    private CreateUserRequest ownerDetails;
}
