package com.markdownMaster.doc.services;

import com.markdownMaster.doc.dtos.DocDTO;
import com.markdownMaster.doc.exceptions.UserNotAllowedException;

import java.util.List;

public interface DocService {
    void createDocument(DocDTO docDTO);

    List<DocDTO> fetchDocsForUserId(String userID, String callerUserId);

    DocDTO fetchDoc(String docId, String userId);

    List<DocDTO> fetchTopRecentDocs();

    void updateDoc(DocDTO docDTO, String userId) throws UserNotAllowedException;

}


