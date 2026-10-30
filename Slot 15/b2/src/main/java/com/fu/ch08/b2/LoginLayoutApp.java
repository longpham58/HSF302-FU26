package com.fu.ch08.b2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

/** Entry point for the Chapter 8 B2 layout and login form exercise. */
public class LoginLayoutApp extends Application {
    private static final Map<String, String> ACCOUNTS = Map.of(
            "admin", "Admin@123",
            "student", "Student@123");
    private Scene scene;
    private String rememberedUser;
    private final TextField tfUser = new TextField();
    private final PasswordField pfPass = new PasswordField();
    private final CheckBox chkRemember = new CheckBox("Remember me");
    private final Label lblError = new Label();
    private final Button btnLogin = new Button("Log in");
    private final Button btnExit = new Button("Exit");

    @Override
    public void start(Stage stage) {
        scene = new Scene(buildLoginView(), 680, 480);
        stage.setScene(scene);
        stage.setTitle("B2 - Layout & Login Form");
        stage.setMinWidth(480);
        stage.setMinHeight(400);
        stage.show();
    }

    private Node buildHeader() {
        Label logo = new Label("HSF");
        logo.setStyle("-fx-background-color: #f27024; -fx-text-fill: white;"
                + " -fx-font-weight: bold; -fx-padding: 4 8;");
        Label title = new Label("Student Management System");
        title.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        Label version = new Label("v1.0");
        version.setStyle("-fx-text-fill: #cfd8dc;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, logo, title, spacer, version);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 16, 10, 16));
        header.setStyle("-fx-background-color: #263238;");
        return header;
    }

    private Node buildFooter() {
        CheckBox chkDebug = new CheckBox("Show layout bounds (debug)");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox footer = new HBox(10, chkDebug, spacer,
                new Label("Copyright (C) 2026 HSF302 - Chapter 08"));
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(6, 16, 6, 16));
        footer.setStyle("-fx-background-color: #cfd8dc;");
        return footer;
    }

    private Node buildCenter() {
        Label loginTitle = new Label("LOGIN");
        loginTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        tfUser.setText(rememberedUser == null ? "" : rememberedUser);
        tfUser.setPromptText("Username");
        pfPass.clear();
        pfPass.setPromptText("Password");
        chkRemember.setSelected(rememberedUser != null);
        lblError.setStyle("-fx-text-fill: #d93025;");
        lblError.setWrapText(true);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username"), 0, 0);
        grid.add(tfUser, 1, 0);
        grid.add(new Label("Password"), 0, 1);
        grid.add(pfPass, 1, 1);
        grid.add(chkRemember, 1, 2);
        ColumnConstraints labelColumn = new ColumnConstraints(80);
        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelColumn, inputColumn);

        for (Button button : List.of(btnLogin, btnExit)) {
            button.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(button, Priority.ALWAYS);
        }
        btnLogin.setDefaultButton(true);
        btnExit.setCancelButton(true);
        HBox actions = new HBox(10, btnLogin, btnExit);

        VBox card = new VBox(14, loginTitle, grid, lblError, actions);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(24));
        card.setMaxWidth(380);
        card.setMaxHeight(Region.USE_PREF_SIZE);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 3);");

        StackPane center = new StackPane(card);
        center.setPadding(new Insets(20));
        center.setStyle("-fx-background-color: #eceff1;");

        btnLogin.setOnAction(event -> handleLogin());
        btnExit.setOnAction(event -> confirmExit());
        tfUser.textProperty().addListener((obs, oldValue, newValue) -> lblError.setText(""));
        pfPass.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()) lblError.setText("");
        });
        Platform.runLater(() -> (tfUser.getText().isEmpty() ? tfUser : pfPass).requestFocus());

        return center;
    }

    private void handleLogin() {
        String username = tfUser.getText().trim().toLowerCase();
        String password = pfPass.getText();
        if (username.isEmpty() || password.isEmpty()) {
            lblError.setStyle("-fx-text-fill: #d93025;");
            lblError.setText("Please enter both username and password.");
            (username.isEmpty() ? tfUser : pfPass).requestFocus();
            return;
        }
        if (!password.equals(ACCOUNTS.get(username))) {
            lblError.setStyle("-fx-text-fill: #d93025;");
            lblError.setText("Incorrect username or password.");
            pfPass.clear();
            pfPass.requestFocus();
            return;
        }

        rememberedUser = chkRemember.isSelected() ? username : null;
        lblError.setStyle("-fx-text-fill: #188038;");
        lblError.setText("Login successful.");
    }

    private void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to exit the application?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm exit");
        alert.setHeaderText(null);
        if (scene.getWindow() != null) alert.initOwner(scene.getWindow());
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Platform.exit();
        }
    }

    private Parent buildLoginView() {
        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(buildCenter());
        root.setBottom(buildFooter());
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
