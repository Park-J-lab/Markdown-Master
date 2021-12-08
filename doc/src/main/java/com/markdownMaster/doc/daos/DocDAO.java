package com.markdownMaster.doc.daos;

import com.markdownMaster.doc.models.DocModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocDAO extends MongoRepository<DocModel, String> {

    List<DocModel> findAllByUserIdOrderByUpdatedAtDesc(String userID);

    Page<DocModel> findByAvailable(boolean b, PageRequest updatedAt);

}

