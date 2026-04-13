package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.EmailNotification;
import com.codewithmosh.store.entities.Notification;
import com.codewithmosh.store.entities.PushNotification;
import com.codewithmosh.store.entities.SmsNotification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public String send(Notification notification) {

        if (notification instanceof EmailNotification e) {
            return "Email sent to " + e.email();
        }

        if (notification instanceof SmsNotification s) {
            return "SMS sent to " + s.phone();
        }

        if (notification instanceof PushNotification p) {
            return "Push sent to user " + p.userId();
        }

        throw new IllegalArgumentException("Unknown type");
    }
}
