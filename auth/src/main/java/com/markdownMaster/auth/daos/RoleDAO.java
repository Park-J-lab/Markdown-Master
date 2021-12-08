package com.markdownMaster.auth.daos;

import com.markdownMaster.auth.models.MarkdownRoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends MongoRepository<MarkdownRoleModel, String> {
    Optional<MarkdownRoleModel> findByRole(String role);
}
