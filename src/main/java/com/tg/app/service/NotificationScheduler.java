package com.tg.app.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.tg.app.listener.NotificationTask;
import com.tg.app.repository.NotificationTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationScheduler(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkNotifications() {

        LocalDateTime currentTime = LocalDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES);

        List<NotificationTask> tasks =
                notificationTaskRepository.findAllByNotificationTime(currentTime);

        for (NotificationTask task : tasks) {
            telegramBot.execute(
                    new SendMessage(task.getChatId(), task.getMessage())
            );

            log.info("Найдено {} уведомлений для отправки: {}", tasks.size(), tasks);
        }
    }
}