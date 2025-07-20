package org.example.notificationmessagerie.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount =
                getClass().getResourceAsStream("/firebase/serviceAccountKey.json");

        if (serviceAccount == null) {
            throw new FileNotFoundException("Fichier serviceAccountKey.json introuvable");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirebaseMessaging.getInstance();
    }
}

