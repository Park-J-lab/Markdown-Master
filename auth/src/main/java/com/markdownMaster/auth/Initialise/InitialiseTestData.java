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

@Profile({"dev", "test"})
@Component
public class InitialiseTestData {

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

        roleDAO.deleteAll();

        MarkdownRoleModel markdownRoleModel1 = new MarkdownRoleModel();
        markdownRoleModel1.setRole("ADMIN");

        MarkdownRoleModel markdownRoleModel2 = new MarkdownRoleModel();
        markdownRoleModel2.setRole("USER");

        roleDAO.save(markdownRoleModel1);
        roleDAO.save(markdownRoleModel2);
    }

    void addUsers() {

        userDAO.deleteAll();

        MarkdownUserModel markdownUserModel1 = new MarkdownUserModel();
        markdownUserModel1.setUsername("admin");
        markdownUserModel1.setEmail("admin@admin.com");
        markdownUserModel1.setPassword(bCryptPasswordEncoder.encode("admin"));
        markdownUserModel1.setRoles(Collections.singletonList("ADMIN"));
        markdownUserModel1.setId("c3191105-1803-437c-9720-e94d37efa429");
        tokenService.generateToken(markdownUserModel1);
        markdownUserModel1.setDisplayName("adminDisplayName");

        userDAO.save(markdownUserModel1);

        MarkdownUserModel markdownUserModel2 = new MarkdownUserModel();
        markdownUserModel2.setUsername("user");
        markdownUserModel2.setEmail("user@user.com");
        markdownUserModel2.setPassword(bCryptPasswordEncoder.encode("user"));
        markdownUserModel2.setRoles(Collections.singletonList("USER"));
        markdownUserModel2.setId("1lk23j1kl3j-lkkj3lk124j3kl2-kj234lk23j4");
        tokenService.generateToken(markdownUserModel2);
        markdownUserModel2.setDisplayName("userDisplayName");

        userDAO.save(markdownUserModel2);
    }

}
