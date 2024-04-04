package com.turborvip.core.service;

import com.google.cloud.storage.Storage;
import org.springframework.web.multipart.MultipartFile;

public interface FirebaseService {
    String uploadFirebase(MultipartFile file, String folder) throws Exception;
}
