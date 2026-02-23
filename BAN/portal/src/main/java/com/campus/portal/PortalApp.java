package com.campus.portal;

import com.campus.portal.util.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PortalApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // MAKE SURE DATABASE IS CREATED BEFORE ANY DAO USE
        DatabaseHelper.initializeDatabase();

        FXMLLoader loader = new FXMLLoader(
                PortalApp.class.getResource("/com/campus/portal/home-view.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Student Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}