package com.markdownMaster.auth.Initialise;

import com.markdownMaster.auth.daos.RoleDAO;
import com.markdownMaster.auth.daos.UserDAO;
import com.markdownMaster.auth.models.MarkdownRoleModel;
import com.markdownMaster.auth.models.MarkdownUserModel;
import com.markdownMaster.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Profile({"prod"})
@Component
public class InitialiseProdData {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenService tokenService;


    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        addRoles();
        addUsers();
    }

    void addRoles() {

        final Optional<MarkdownRoleModel> optionalMarkdownRoleModelAdmin = roleDAO.findByRole("ADMIN");
        if (!optionalMarkdownRoleModelAdmin.isPresent()) {
            MarkdownRoleModel markdownRoleModelAdmin = new MarkdownRoleModel();
            markdownRoleModelAdmin.setRole("ADMIN");
            roleDAO.save(markdownRoleModelAdmin);
        }

        final Optional<MarkdownRoleModel> optionalMarkdownRoleModelUser = roleDAO.findByRole("USER");
        if (!optionalMarkdownRoleModelUser.isPresent()) {
            MarkdownRoleModel markdownRoleModelUser = new MarkdownRoleModel();
            markdownRoleModelUser.setRole("USER");
            roleDAO.save(markdownRoleModelUser);
        }
    }

    void addUsers() {

        final Optional<MarkdownUserModel> optionalMarkdownUserModelAdmin = userDAO.findByUsername("admin");

        if (!optionalMarkdownUserModelAdmin.isPresent()) {
            MarkdownUserModel markdownUserModelAdmin = new MarkdownUserModel();
            markdownUserModelAdmin.setUsername("admin");
            markdownUserModelAdmin.setEmail("admin@admin.com");
            markdownUserModelAdmin.setPassword(bCryptPasswordEncoder.encode("12j3hka838ah"));
            markdownUserModelAdmin.setRoles(Collections.singletonList("ADMIN"));
            tokenService.generateToken(markdownUserModelAdmin);
            markdownUserModelAdmin.setDisplayName("adminDisplayName");

            userDAO.save(markdownUserModelAdmin);
        }

        final Optional<MarkdownUserModel> optionalMarkdownUserModelUser = userDAO.findByUsername("user");

        if (!optionalMarkdownUserModelUser.isPresent()) {
            MarkdownUserModel markdownUserModelUser = new MarkdownUserModel();
            markdownUserModelUser.setUsername("user");
            markdownUserModelUser.setEmail("user@user.com");
            markdownUserModelUser.setPassword(bCryptPasswordEncoder.encode("2kj345kjhsdfuia"));
            markdownUserModelUser.setRoles(Collections.singletonList("USER"));
            tokenService.generateToken(markdownUserModelUser);
            markdownUserModelUser.setDisplayName("userDisplayName");

            userDAO.save(markdownUserModelUser);
        }
    }

}
