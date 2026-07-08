package com.tg.app.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.tg.app.repository.NotificationTaskRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final Pattern PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)"
    );

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    @Autowired
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener((UpdatesListener) this);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {

            if (update.message() == null || update.message().text() == null) {
                continue;
            }

            String message = update.message().text();
            Long chatId = update.message().chat().id();

            if ("/start".equals(message)) {
                telegramBot.execute(new SendMessage(chatId, "Здравствуй!"));
                continue;
            }

            Matcher matcher = PATTERN.matcher(message);

            if (matcher.matches()) {

                LocalDateTime dateTime =
                        LocalDateTime.parse(
                                matcher.group(1),
                                DATE_TIME_FORMATTER);

                NotificationTask task = new NotificationTask();
                task.setChatId(update.message().chat().id());
                task.setNotificationTime(dateTime);
                task.setMessage(matcher.group(3));

                notificationTaskRepository.save(task);

                telegramBot.execute(new SendMessage(chatId, "Напоминание сохранено!"));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
