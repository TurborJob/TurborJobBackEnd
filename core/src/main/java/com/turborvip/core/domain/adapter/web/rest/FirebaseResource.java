package com.turborvip.core.domain.adapter.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FirebaseResource {
    @PostMapping("/all/upload-file")
    ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file);
}
