package com.example.phonebook.statistics.service;

import com.example.phonebook.statistics.database.entity.Statistic;
import com.example.phonebook.statistics.database.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StatisticService {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public List<Statistic> getAll() {
        return statisticRepository.findAll();
    }

    public Statistic create(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    public void delete(int id) {
        statisticRepository.deleteById(id);
    }
}
