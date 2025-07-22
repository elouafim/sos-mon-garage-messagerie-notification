package org.example.notificationmessagerie.service.imp;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

import com.google.firebase.messaging.Message;
import org.example.notificationmessagerie.entitie.Notification;
import org.example.notificationmessagerie.entitie.dto.TypeNotification;
import org.example.notificationmessagerie.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repo;
    @Autowired
    private SimpMessagingTemplate ws;
    @Autowired
    private FirebaseMessaging firebase;

    public Notification notify(Long userId, TypeNotification type, String payload) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setType(type);
        n.setPayload(payload);
        repo.save(n);

        // Envoi temps réel via WebSocket
        ws.convertAndSendToUser(userId.toString(), "/queue/notifications", n);

        // Envoi push via Firebase Cloud Messaging (FCM)
        try {
            // Construire le message FCM
           /* Message message = Message.builder()
                    .setToken("") // méthode à définir pour récupérer le token FCM de l'utilisateur
                    .putData("type", type.toString())
                    .putData("payload", payload)
                    .build();

            */

            Message message=null;

            String response = firebase.send(message);
            System.out.println("FCM message sent successfully: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Erreur lors de l'envoi FCM : " + e.getMessage());
        }

        return n;
    }


    public List<Notification> unread(Long userId) {
        return repo.findByUserIdAndReadFalse(userId);
    }

    public void markRead(Long notifId) {
        repo.findById(notifId).ifPresent(n -> {
            n.setRead(true);
            repo.save(n);
        });
    }
}
