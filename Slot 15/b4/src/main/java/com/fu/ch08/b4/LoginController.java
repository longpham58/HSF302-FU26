package com.fu.ch08.b4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Map;

public class LoginController {
    @FXML private TextField tfUsername;
    @FXML private TextField tfPasswordVisible;
    @FXML private PasswordField pfPassword;
    @FXML private CheckBox chkShow;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    private static final Map<String, String[]> ACCOUNTS = Map.of(
            "student", new String[]{"Student@123", "Sinh viên"},
            "teacher", new String[]{"Teacher@123", "Giảng viên"});
    private int failedCount;

    @FXML
    public void initialize() {
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());
        tfPasswordVisible.visibleProperty().bind(chkShow.selectedProperty());
        tfPasswordVisible.managedProperty().bind(chkShow.selectedProperty());
        pfPassword.visibleProperty().bind(chkShow.selectedProperty().not());
        pfPassword.managedProperty().bind(chkShow.selectedProperty().not());

        btnLogin.disableProperty().bind(tfUsername.textProperty().isEmpty()
                .or(pfPassword.textProperty().isEmpty())
                .or(cbRole.valueProperty().isNull()));
        Platform.runLater(tfUsername::requestFocus);
    }

    @FXML
    private void handleLogin() {
        // Login behavior is added in TODO 4.
    }

    @FXML
    private void handleForgot() {
        // Password recovery behavior is added in TODO 4.
    }
}
