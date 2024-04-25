package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.FirebaseResource;
import com.turborvip.core.domain.http.response.FileResponse;
import com.turborvip.core.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestApiV1
public class FirebaseResourceImpl implements FirebaseResource {
    @Autowired
    private  FirebaseService firebaseService;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        try {
            FileResponse result = new FileResponse(firebaseService.uploadFirebase(file, "images"));
            return VsResponseUtil.ok("File uploaded successfully!", result);
        } catch (IOException e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST,"Failed to upload file");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
