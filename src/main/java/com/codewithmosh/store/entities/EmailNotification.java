package com.codewithmosh.store.entities;

public record EmailNotification(String email, String content) implements  Notification{
}
