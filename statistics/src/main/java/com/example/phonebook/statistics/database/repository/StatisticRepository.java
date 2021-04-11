package com.example.phonebook.statistics.database.repository;

import com.example.phonebook.statistics.database.entity.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends MongoRepository<Statistic, Integer> {
}
