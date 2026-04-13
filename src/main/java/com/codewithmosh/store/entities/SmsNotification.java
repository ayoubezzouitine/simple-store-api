package com.codewithmosh.store.entities;

public record SmsNotification(String phone, String content) implements Notification{
}
