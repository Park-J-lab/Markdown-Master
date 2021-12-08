package com.markdownMaster.auth.services.impl;

import com.markdownMaster.auth.daos.RoleDAO;
import com.markdownMaster.auth.dtos.RoleDTO;
import com.markdownMaster.auth.models.MarkdownRoleModel;
import com.markdownMaster.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createRole(RoleDTO roleDTO) {

        MarkdownRoleModel markdownRoleModel = modelMapper.map(roleDTO, MarkdownRoleModel.class);

        roleDAO.save(markdownRoleModel);

        modelMapper.map(markdownRoleModel, roleDTO);
    }


    @Override
    public RoleDTO roleInfo(String roleId) {

        Optional<MarkdownRoleModel> markdownRoleModelOptional = roleDAO.findById(roleId);

        if (markdownRoleModelOptional.isPresent()) {
            final MarkdownRoleModel markdownRoleModel = markdownRoleModelOptional.get();

            return modelMapper.map(markdownRoleModel, RoleDTO.class);
        }

        return null;
    }

}
