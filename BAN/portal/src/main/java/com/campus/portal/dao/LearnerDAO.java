package com.campus.portal.dao;

import com.campus.portal.model.Learner;

import java.util.List;
import java.util.Optional;

public interface LearnerDAO {

    void insert(Learner learner);

    void update(Learner learner);

    void delete(String regNumber);

    Optional<Learner> findByRegNumber(String regNumber);

    List<Learner> findAll();

    List<Learner> search(String keyword);
}