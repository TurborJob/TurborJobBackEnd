package com.turborvip.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseApplication {
    public static void main(String[] args) {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("tokensAccess/turborjob-c336e-firebase-adminsdk-bh4zn-be76f1e4e3.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Firebase initialized successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
