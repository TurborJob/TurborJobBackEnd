package com.turborvip.core.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.turborvip.core.config.application.EcommerceProperties;
import com.turborvip.core.service.FirebaseService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {
    @Autowired
    EcommerceProperties ecommerceProperties;
    private Bucket bucket;

    @PostConstruct
    public void initialize() throws IOException {
        // Lấy InputStream của tệp JSON từ thư mục resources
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("tokensAccess/turborjob-c336e-firebase-adminsdk-bh4zn-be76f1e4e3.json");
        if (serviceAccount == null) {
            throw new IOException("Service account file not found");
        }

        // Khởi tạo FirebaseApp
        FirebaseApp.initializeApp(FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build());

        // Lấy đối tượng Storage từ Firebase
        bucket  = StorageClient.getInstance().bucket(ecommerceProperties.getBucketName());
    }

    @Override
    public String uploadFirebase(MultipartFile file, String folder) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        if(file.getSize() > 1000000){
            throw new Exception("File must less than 10MB ");
        }

        // Lấy tên file
        String fileName = folder + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Tải lên hình ảnh vào Cloud Storage của Firebase
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
        String downloadUrl = String.valueOf(blob.signUrl(10000000 /* valid for 1 hour */, TimeUnit.SECONDS));
        return downloadUrl;
    }
}
