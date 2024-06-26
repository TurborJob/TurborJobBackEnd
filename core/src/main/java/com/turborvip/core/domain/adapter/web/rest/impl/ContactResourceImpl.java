package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.ContactResource;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.ContactService;
import com.turborvip.core.service.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class ContactResourceImpl implements ContactResource {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<RestData<?>> createContact(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            String name = (String) requestBody.get("name");
            String content = (String) requestBody.get("content");
            String email = (String) requestBody.get("email");

            contactService.createContact(name, content, email);
            return VsResponseUtil.ok("Create contact successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> replyContact(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int idContact = (int) requestBody.get("idContact");
            String content = (String) requestBody.get("content");
            User admin = authService.getUserByHeader(request);
            contactService.replyContact(admin, idContact, content);
            return VsResponseUtil.ok("Reply successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getAllContact(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get contact successfully!", contactService.getContact(page, size));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
