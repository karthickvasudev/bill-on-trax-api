package com.billontrax.common.models;

import com.billontrax.modules.role.RoleName;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigInteger;

@Data
@Component
@RequestScope
public class CurrentUser {
    private BigInteger userId;
    private RoleName roleName;
}
