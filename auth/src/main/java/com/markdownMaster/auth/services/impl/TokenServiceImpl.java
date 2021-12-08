package com.markdownMaster.auth.services.impl;

import com.markdownMaster.auth.exceptions.InvalidTokenException;
import com.markdownMaster.auth.models.MarkdownUserModel;
import com.markdownMaster.auth.services.AuthSigningKeyResolver;
import com.markdownMaster.auth.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@Log4j2
public class TokenServiceImpl implements TokenService {
    @Autowired
    AuthSigningKeyResolver authSigningKeyResolver;

    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {

        checkNotNull(jwtToken);

        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigningKeyResolver)
                    .build()
                    .parse(jwtToken);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.warn("Exception at token validation");
            throw new InvalidTokenException("Invalid token", e);
        }
    }


    @Override
    public void generateToken(MarkdownUserModel markdownUserModel) {

        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(markdownUserModel.getUsername())
                .setAudience(markdownUserModel.getRoles().toString())
                .setIssuer(markdownUserModel.getId())
                .signWith(authSigningKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();

        markdownUserModel.setJwtToken(jwtToken);
    }

}
