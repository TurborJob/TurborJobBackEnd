package com.turborvip.firebase.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirebaseStorageService {

    private final Storage storage;

    public FirebaseStorageService(String serviceAccountPath) throws IOException {
        FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

        // Khởi tạo Firebase
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);

        // Khởi tạo Storage
        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }

    public void uploadFile(String bucketName, String localFilePath, String storageFilePath) throws IOException {
        // Tải tệp lên Firebase Storage
        BlobId blobId = BlobId.of(bucketName, storageFilePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] fileBytes = Files.readAllBytes(Paths.get(localFilePath));
        Blob blob = storage.create(blobInfo, fileBytes);

        System.out.println("File uploaded successfully. Storage path: " + blob.getName());
    }
}
