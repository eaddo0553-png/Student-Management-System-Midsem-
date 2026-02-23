package com.campus.portal.controller;

import com.campus.portal.dao.SQLiteLearnerDAO;
import com.campus.portal.logic.AnalyticsManager;
import com.campus.portal.model.Learner;
import com.campus.portal.util.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AnalyticsController {

    @FXML private TableView<Learner> table;
    @FXML private TableColumn<Learner,String> regCol;
    @FXML private TableColumn<Learner,String> nameCol;
    @FXML private TableColumn<Learner,Double> scoreCol;

    private final AnalyticsManager analytics =
            new AnalyticsManager(new SQLiteLearnerDAO());

    private final ObservableList<Learner> data =
            FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        regCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getRegNumber()));
        nameCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        scoreCol.setCellValueFactory(c->new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getAverageScore()));
        table.setItems(data);
    }

    @FXML
    private void top10(){
        data.setAll(analytics.topPerformers(10));
    }

    @FXML
    private void under2(){
        data.setAll(analytics.underPerforming(2.0));
    }

    @FXML
    private void goBack(javafx.event.ActionEvent e){
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/com/campus/portal/home-view.fxml",
                "Student Management System");
    }
}