package org.example.notificationmessagerie.service.imp;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.example.notificationmessagerie.entitie.dto.TypeNotification;
import org.example.notificationmessagerie.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotification(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        return firebaseMessaging.send(message);
    }
}

