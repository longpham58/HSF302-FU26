package com.fu.ch08.b1;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the Chapter 8, B1 drink-ordering application.
 * UI setup will be added in the following steps.
 */
public class DrinkOrderApp extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("B1 - Đặt đồ uống");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
