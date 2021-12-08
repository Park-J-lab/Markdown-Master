package com.markdownMaster.doc.controllers;

import com.markdownMaster.doc.dtos.DocDTO;
import com.markdownMaster.doc.exceptions.UserNotAllowedException;
import com.markdownMaster.doc.services.DocService;
import com.markdownMaster.doc.services.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Autowired
    DocService docService;

    @Autowired
    TokenService tokenService;

    //- create his own docs
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DocDTO createDocument(@RequestBody DocDTO docDTO) {

        docService.createDocument(docDTO);

        return docDTO;
    }


    //    - fetch his own documents
    @GetMapping("/all/{userId}")
    @PreAuthorize("hasAnyRole('ANONYMOUS', 'USER', 'ADMIN')")
    public List<DocDTO> fetchUserDocs(@PathVariable String userId, HttpServletRequest httpServletRequest) {

        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String callerUserId = tokenService.getUserId(jwtToken);

        return docService.fetchDocsForUserId(userId, callerUserId);
    }

    //- fetch a public document
    @GetMapping("/fetch/{docId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ANONYMOUS')")
    public DocDTO fetchDocument(@PathVariable String docId, HttpServletRequest httpServletRequest) {

        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String userId = tokenService.getUserId(jwtToken);

        return docService.fetchDoc(docId, userId);
    }

    //- fetch 10 most recents docs that are public
    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ANONYMOUS')")
    public List<DocDTO> fetRecentDocs() {

        return docService.fetchTopRecentDocs();
    }

    //- modify his own documents
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DocDTO updateDoc(@RequestBody DocDTO docDTO, HttpServletRequest httpServletRequest) throws UserNotAllowedException {

        String jwtToken = getJwtTokenFromHeader(httpServletRequest);

        String userId = tokenService.getUserId(jwtToken); //userId -> issuer on jwtToken

        docService.updateDoc(docDTO, userId);

        return docDTO;
    }

    private String getJwtTokenFromHeader(HttpServletRequest httpServletRequest) {

        try {
            String tokenHeader = httpServletRequest.getHeader(AUTHORIZATION);
            return StringUtils.removeStart(tokenHeader, "Bearer ").trim();
        } catch (NullPointerException e) {
            return StringUtils.EMPTY;
        }
    }

    //- delete his own docs
    // TODO: homework
}
