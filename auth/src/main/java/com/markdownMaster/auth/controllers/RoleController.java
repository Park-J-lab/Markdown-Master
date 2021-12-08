package com.markdownMaster.auth.controllers;


import com.markdownMaster.auth.dtos.RoleDTO;
import com.markdownMaster.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This file was created by aantonica on 19/05/2020
 */
@RestController
@RequestMapping("/role")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {

    @Autowired
    RoleService roleService;

    //- create role
    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {

        checkNotNull(roleDTO);
        roleService.createRole(roleDTO);
        return roleDTO;
    }

    //- get info about a specific role
    @GetMapping("/info/{roleId}")
    public RoleDTO getRoleInfo(@PathVariable String roleId) {

//        return roleService.roleInfo(roleId);
        System.out.println(roleId);
        return null;
    }

//- delete a role
    //TODO: homework
//- modify a role
    //TODO: homework
}

