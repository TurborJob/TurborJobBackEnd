package com.turborvip.core.service;

import com.turborvip.core.model.entity.Contact;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ContactService {
    void createContact(String name, String content, String email) throws Exception;

    void replyContact(User admin, long idContent, String content) throws Exception;

    Page<Contact> getContact(int page, int size) throws Exception;

}
