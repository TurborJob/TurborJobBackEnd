package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.ContactRepository;
import com.turborvip.core.model.entity.Contact;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AuthService authService;


    @Override
    public void createContact(String name, String content, String email) throws Exception {
        Contact contact = new Contact(name, email, content);
        contactRepository.save(contact);
    }

    @Override
    public void replyContact(HttpServletRequest request, Long idContent, String content) throws Exception {
        User user = authService.getUserByHeader(request);
        Contact contact = contactRepository.findById(idContent).orElse(null);
        if (contact == null) {
            throw new Exception("Contact invalid!");
        }
        Date now = new Date();
        contact.setUser(user);
        contact.setUpdateAt(new Timestamp(now.getTime()));
        contactRepository.save(contact);
        new GMailerServiceImpl().sendEmail(contact.getEmail(),
                "Thanks for your contact!",
                content);
    }

    @Override
    public Page<Contact> getContact(int page, int size) throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findAll(pageable);
    }

}
