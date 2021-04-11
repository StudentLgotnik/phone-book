package com.example.phonebook.statistics.listeners;

import com.example.phonebook.statistics.database.entity.Statistic;
import com.example.phonebook.dto.Event;
import com.example.phonebook.statistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final StatisticService statisticService;

    @Autowired
    public EventListener(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @KafkaListener(topics = "phonebook", groupId = "phonebook-statistic", containerFactory = "eventKafkaListenerContainerFactory")
    public void listenEvents(Event event) {
        Statistic receivedEvent = new Statistic("New User added: " + event);
        statisticService.create(receivedEvent);

    }
}
