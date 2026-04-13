package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.NotificationRequestDTO;
import com.codewithmosh.store.entities.EmailNotification;
import com.codewithmosh.store.entities.Notification;
import com.codewithmosh.store.entities.PushNotification;
import com.codewithmosh.store.entities.SmsNotification;
import com.codewithmosh.store.services.NotificationService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<String> getNotifcationMessage(){
        return ResponseEntity.ok("Welcome to notifications");
    }

    @PostMapping
    public String send(@RequestBody NotificationRequestDTO dto) {

        Notification notification = switch (dto.getType()) {
            case "EMAIL" -> new EmailNotification(dto.getDestination(), dto.getContent());
            case "SMS" -> new SmsNotification(dto.getDestination(), dto.getContent());
            case "PUSH" -> new PushNotification(Long.parseLong(dto.getDestination()), dto.getContent());
            default -> throw new IllegalArgumentException("Unknown type");
        };

        return service.send(notification);
    }
}
