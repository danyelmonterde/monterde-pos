package com.monterdev.monterdepos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 640);
        stage.setTitle("Welcome to Alen Cai Grocery Store!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}