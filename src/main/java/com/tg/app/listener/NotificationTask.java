package com.tg.app.listener;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")

public class NotificationTask {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String message;
    private LocalDateTime dateTime;

    public NotificationTask() {

    }

    public NotificationTask(Long id, Long chatId, String message, LocalDateTime dateTime) {
        this.id = id;
        this.chatId = chatId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotificationTime() {
        return dateTime;
    }

    public void setNotificationTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
