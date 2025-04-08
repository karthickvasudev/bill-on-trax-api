package com.billontrax.modules.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleUserMapRepository roleUserMapRepository;

    public void assignRole(BigInteger userId, BigInteger roleId){
        RoleUserMap roleUserMap = new RoleUserMap();
        roleUserMap.setRoleId(roleId);
        roleUserMap.setUserId(userId);
        roleUserMapRepository.save(roleUserMap);
    }
}
