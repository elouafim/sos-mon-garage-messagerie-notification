package org.example.notificationmessagerie.controller;

import org.example.notificationmessagerie.entitie.Notification;
import org.example.notificationmessagerie.service.imp.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService svc;

    @GetMapping("/unread")
    public List<Notification> unread() {
        Long id=null;
        return svc.unread(id);
    }

    @PostMapping("/read/{id}")
    public void markRead(@PathVariable Long id) {
        svc.markRead(id);
    }
}