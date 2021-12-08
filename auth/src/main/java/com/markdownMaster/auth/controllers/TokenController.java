package com.markdownMaster.auth.controllers;

import com.markdownMaster.auth.services.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * This file was created by aantonica on 19/05/2020
 */

@RestController
@RequestMapping("/token")
@Log4j2
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/validate")
    @PreAuthorize("hasAnyRole('ANONYMOUS', 'USER', 'ADMIN')")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception {

        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);

        String token = null; //        Bearer 4jkn2.24kn234kj2n

        log.info("Started validating header " + authHeader);
        if (!isEmpty(authHeader)) {
            token = authHeader.split("\\s")[1];
        } else {
            log.info("Nothing to validate");
            return;
        }
//
//        log.info("Validating");
//        tokenService.validateToken(token);
        tokenService.validateToken(token);
    }
}

