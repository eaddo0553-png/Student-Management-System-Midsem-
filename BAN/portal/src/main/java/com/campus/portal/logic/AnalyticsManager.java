package com.campus.portal.logic;

import com.campus.portal.dao.LearnerDAO;
import com.campus.portal.model.Learner;

import java.util.Comparator;
import java.util.List;

public class AnalyticsManager {

    private final LearnerDAO dao;

    public AnalyticsManager(LearnerDAO dao) {
        this.dao = dao;
    }

    public List<Learner> topPerformers(int limit) {

        return dao.findAll().stream()
                .sorted(Comparator.comparingDouble(Learner::getAverageScore)
                        .reversed())
                .limit(limit)
                .toList();
    }

    public List<Learner> underPerforming(double threshold) {

        return dao.findAll().stream()
                .filter(l -> l.getAverageScore() < threshold)
                .toList();
    }
}