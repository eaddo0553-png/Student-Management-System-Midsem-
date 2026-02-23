package com.campus.portal.model;

import java.time.LocalDate;

public class Learner {

    private String regNumber;
    private String name;
    private String course;
    private int yearLevel;
    private double averageScore;
    private String emailAddress;
    private String contactNumber;
    private LocalDate createdOn;
    private String enrollmentStatus;

    public Learner() {}

    public Learner(String regNumber, String name, String course,
                   int yearLevel, double averageScore,
                   String emailAddress, String contactNumber,
                   LocalDate createdOn, String enrollmentStatus) {

        this.regNumber = regNumber;
        this.name = name;
        this.course = course;
        this.yearLevel = yearLevel;
        this.averageScore = averageScore;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.createdOn = createdOn;
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }

    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public LocalDate getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDate createdOn) { this.createdOn = createdOn; }

    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }
}