package com.codewithmosh.store.entities;

public record PushNotification(Long userId, String content) implements Notification{
}
