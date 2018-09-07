package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Ui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My first thing");
        primaryStage.show();
    }
}
