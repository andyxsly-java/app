package com.tg.app.listener;

import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")

public class NotificationTask {

    private Long chat_id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String key;
    private String text;
    private float date;
    private float time;

    public NotificationTask() {

    }

    public NotificationTask(Long chat_id, long id, String text, float date, float time) {
        this.chat_id = chat_id;
        this.id = id;
        this.text = text;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChatId() {
        return chat_id;
    }

    public void setChatId(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getDate() {
        return date;
    }

    public void setDate(float date) {
        this.date = date;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setChatId(Long chat_id) {
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
    }

}
