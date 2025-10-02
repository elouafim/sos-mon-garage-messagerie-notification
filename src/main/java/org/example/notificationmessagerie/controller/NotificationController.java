package org.example.notificationmessagerie.controller;

import org.example.notificationmessagerie.entitie.Notification;
import org.example.notificationmessagerie.service.imp.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String token,
                                                   @RequestParam String title,
                                                   @RequestParam String body) {
        try {
            String response = notificationService.sendNotification(token, title, body);
            return ResponseEntity.ok("Notification envoyée : " + response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
        }
    }
}
