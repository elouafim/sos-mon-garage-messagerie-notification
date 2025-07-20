package org.example.notificationmessagerie.entitie;

import jakarta.persistence.*;
import lombok.Data;
import org.example.notificationmessagerie.entitie.dto.TypeNotification;

import java.time.Instant;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    @Enumerated(EnumType.STRING)
    private TypeNotification type;
    private String payload;

    private Instant timestamp = Instant.now();
    private boolean read = false;


}
