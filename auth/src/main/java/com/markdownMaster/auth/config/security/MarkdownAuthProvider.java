package com.markdownMaster.auth.config.security;

import com.markdownMaster.auth.daos.UserDAO;
import com.markdownMaster.auth.exceptions.InvalidTokenException;
import com.markdownMaster.auth.exceptions.MarkdownTokenAuthException;
import com.markdownMaster.auth.models.MarkdownUserModel;
import com.markdownMaster.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class MarkdownAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        final String token = (String) authentication.getCredentials();// this will be the jwtToken

        if (isEmpty(token)) {
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        // find user based on token
        Optional<MarkdownUserModel> markdownUserModelOptional = userDAO.findByJwtToken(token);

        if (markdownUserModelOptional.isPresent()) {
            final MarkdownUserModel markdownUserModel = markdownUserModelOptional.get();

            try {
                tokenService.validateToken(token);
            } catch (InvalidTokenException e) {
                markdownUserModel.setJwtToken(null);
                userDAO.save(markdownUserModel);

                return null;
            }

            return new User(username, "",
                    AuthorityUtils.createAuthorityList(
                            markdownUserModel.getRoles().stream()
                                    .map(roleName -> "ROLE_" + roleName)
                                    .toArray(String[]::new)
                    )
            );
        }

        throw new MarkdownTokenAuthException("User not found for token :" + token);
    }
}

