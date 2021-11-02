/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Pedro Henrique Sotto-Mayor Pereira da Silva
 */
package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        //launch application
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //set homepage scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/ucf.assignments/homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Todo List App");
        stage.setScene(scene);
        stage.show();
    }

}