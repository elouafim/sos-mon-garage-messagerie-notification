package org.example.notificationmessagerie.service.imp;

import com.google.firebase.messaging.FirebaseMessaging;
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
        // envoi temps réel
        ws.convertAndSendToUser(
                userId.toString(), "/queue/notifications", n);
        // push web/mobile via FCM (optionnel)
        // firebase.send(...);
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
