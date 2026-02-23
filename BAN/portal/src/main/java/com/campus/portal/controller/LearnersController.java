package com.campus.portal.controller;

import com.campus.portal.dao.SQLiteLearnerDAO;
import com.campus.portal.logic.LearnerManager;
import com.campus.portal.model.Learner;
import com.campus.portal.util.CSVHelper;
import com.campus.portal.util.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class LearnersController {

    @FXML private TableView<Learner> table;
    @FXML private TableColumn<Learner,String> regCol;
    @FXML private TableColumn<Learner,String> nameCol;
    @FXML private TableColumn<Learner,String> courseCol;
    @FXML private TableColumn<Learner,Integer> levelCol;
    @FXML private TableColumn<Learner,Double> scoreCol;
    @FXML private TableColumn<Learner,String> statusCol;

    @FXML private TextField regField;
    @FXML private TextField nameField;
    @FXML private TextField courseField;
    @FXML private TextField levelField;
    @FXML private TextField scoreField;
    @FXML private TextField emailField;
    @FXML private TextField contactField;
    @FXML private ComboBox<String> statusBox;

    private final LearnerManager manager =
            new LearnerManager(new SQLiteLearnerDAO());

    private final ObservableList<Learner> data =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        regCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRegNumber()));
        nameCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        courseCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCourse()));
        levelCol.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getYearLevel()));
        scoreCol.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getAverageScore()));
        statusCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEnrollmentStatus()));

        statusBox.getItems().addAll("Active","Inactive");

        load();
    }

    private void load() {
        data.setAll(manager.getAll());
        table.setItems(data);
    }

    @FXML
    private void add() {
        try {
            manager.register(build());
            load();
        } catch(Exception e){
            error(e.getMessage());
        }
    }

    @FXML
    private void update() {
        try {
            manager.modify(build());
            load();
        } catch(Exception e){
            error(e.getMessage());
        }
    }

    @FXML
    private void delete() {
        Learner selected = table.getSelectionModel().getSelectedItem();
        if(selected==null){ error("Select a record"); return; }
        manager.remove(selected.getRegNumber());
        load();
    }

    @FXML
    private void exportCSV(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV","*.csv"));
            File file = fc.showSaveDialog(table.getScene().getWindow());
            if(file!=null){
                CSVHelper.exportData(manager.getAll(), file);
                info("Export completed");
            }
        }catch(Exception e){ error(e.getMessage()); }
    }

    @FXML
    private void importCSV(){
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV","*.csv"));
            File file = fc.showOpenDialog(table.getScene().getWindow());
            if(file!=null){
                List<Learner> imported = CSVHelper.importData(file);
                for(Learner l : imported){
                    try {
                        manager.register(l);  // will skip/throw if reg exists
                    } catch (IllegalArgumentException ex) {
                        // Optional: log or alert duplicates
                        System.out.println("Skipped duplicate: " + l.getRegNumber());
                    }
                }
                load();
                info("Import completed (" + imported.size() + " records processed)");
            }
        }catch(Exception e){
            error("Import failed: " + e.getMessage());
        }
    }

    @FXML
    private void goBack(javafx.event.ActionEvent e){
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage,
                "/com/campus/portal/home-view.fxml",
                "Student Management System");
    }

    private Learner build(){
        return new Learner(
                regField.getText(),
                nameField.getText(),
                courseField.getText(),
                Integer.parseInt(levelField.getText()),
                Double.parseDouble(scoreField.getText()),
                emailField.getText(),
                contactField.getText(),
                LocalDate.now(),
                statusBox.getValue()
        );
    }

    private void error(String m){
        Alert a=new Alert(Alert.AlertType.ERROR);
        a.setContentText(m);
        a.showAndWait();
    }

    private void info(String m){
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(m);
        a.showAndWait();
    }
}