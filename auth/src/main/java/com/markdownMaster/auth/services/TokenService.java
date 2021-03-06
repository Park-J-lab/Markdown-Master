package com.markdownMaster.auth.services;

import com.markdownMaster.auth.exceptions.InvalidTokenException;
import com.markdownMaster.auth.models.MarkdownUserModel;

import java.util.List;

public interface TokenService {
    //    Token validation
    void validateToken(String jwtToken) throws InvalidTokenException;

    void generateToken(MarkdownUserModel markdownUserModel);

    List<String> getRolesFromToken(String jwtToken);

    String getUserIdFromToken(String jwtToken);

}
