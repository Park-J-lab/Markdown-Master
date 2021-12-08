package com.markdownMaster.auth.services;

import com.markdownMaster.auth.dtos.RoleDTO;

public interface RoleService {

    // role creation
    void createRole(RoleDTO roleDTO);

    // role information
    RoleDTO roleInfo(String roleId);
}
