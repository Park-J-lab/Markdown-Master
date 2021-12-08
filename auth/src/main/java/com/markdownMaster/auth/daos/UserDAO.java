package com.markdownMaster.auth.daos;

import com.markdownMaster.auth.models.MarkdownUserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<MarkdownUserModel, String> {

    Optional<MarkdownUserModel> findByUsername(String username);
    Optional<MarkdownUserModel> findByJwtToken(String jwtToken);
    List<MarkdownUserModel> findByDisplayNameOrUsernameOrEmail(String username);
}

