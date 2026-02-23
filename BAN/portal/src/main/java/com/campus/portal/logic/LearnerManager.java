package com.campus.portal.logic;

import com.campus.portal.dao.LearnerDAO;
import com.campus.portal.model.Learner;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class LearnerManager {

    private final LearnerDAO dao;

    public LearnerManager(LearnerDAO dao) {
        this.dao = dao;
    }

    public void register(Learner learner) {

        validate(learner);

        Optional<Learner> existing =
                dao.findByRegNumber(learner.getRegNumber());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Registration number already exists");
        }

        dao.insert(learner);
    }

    public void modify(Learner learner) {
        validate(learner);
        dao.update(learner);
    }

    public void remove(String regNumber) {
        dao.delete(regNumber);
    }

    public List<Learner> getAll() {
        return dao.findAll();
    }

    public List<Learner> search(String keyword) {
        return dao.search(keyword);
    }

    private void validate(Learner l) {

        if (!l.getRegNumber().matches("[A-Za-z0-9]{4,20}"))
            throw new IllegalArgumentException("Invalid registration number");

        if (l.getAverageScore() < 0 || l.getAverageScore() > 4)
            throw new IllegalArgumentException("Score must be 0.0 - 4.0");

        if (l.getEmailAddress() != null &&
                !l.getEmailAddress().contains("@"))
            throw new IllegalArgumentException("Invalid email");

        if (l.getContactNumber() != null &&
                !Pattern.matches("\\d{10,15}", l.getContactNumber()))
            throw new IllegalArgumentException("Invalid contact number");
    }
}