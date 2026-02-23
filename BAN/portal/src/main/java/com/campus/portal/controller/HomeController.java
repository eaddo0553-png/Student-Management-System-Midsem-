package com.campus.portal.controller;

import com.campus.portal.util.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HomeController {

    public void openLearners(ActionEvent e) {

        Stage stage = (Stage) ((Node) e.getSource())
                .getScene().getWindow();

        SceneNavigator.switchScene(stage,
                "/com/campus/portal/learners-view.fxml",
                "Student Management System");
    }

    public void openAnalytics(ActionEvent e) {

        Stage stage = (Stage) ((Node) e.getSource())
                .getScene().getWindow();

        SceneNavigator.switchScene(stage,
                "/com/campus/portal/analytics-view.fxml",
                "Student Management System");
    }
}