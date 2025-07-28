package com.billontrax.common.dtos;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class CurrentUser {
    private Long userId;
}
