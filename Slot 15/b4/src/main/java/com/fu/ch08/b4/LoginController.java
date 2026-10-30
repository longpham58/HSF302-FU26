package com.fu.ch08.b4;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
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
        String username = tfUsername.getText().trim().toLowerCase(Locale.ROOT);
        String password = pfPassword.getText();
        String role = cbRole.getValue();

        if (failedCount >= 3) {
            lblError.setText("Bạn đã nhập sai 3 lần. Vui lòng khởi động lại ứng dụng.");
            return;
        }
        if (!username.matches("[a-z0-9_]{3,20}")) {
            lblError.setText("Tên đăng nhập phải có 3–20 ký tự: chữ thường, số hoặc dấu _.");
            return;
        }

        String[] account = ACCOUNTS.get(username);
        if (account == null || !account[0].equals(password)) {
            failedCount++;
            lblError.setText("Sai tên đăng nhập hoặc mật khẩu (còn %d lần)."
                    .formatted(3 - failedCount));
            pfPassword.clear();
            return;
        }
        if (!account[1].equals(role)) {
            lblError.setText("Vai trò không đúng với tài khoản.");
            return;
        }

        lblError.setText("");
        openWelcome(username, role, (Stage) btnLogin.getScene().getWindow());
    }

    @FXML
    private void handleForgot() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Quên mật khẩu");
        dialog.setHeaderText("Nhập email để lấy lại mật khẩu");
        dialog.showAndWait().ifPresent(email -> {
            if (email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
                AlertUtil.info("Đã gửi hướng dẫn tới " + email);
            } else {
                AlertUtil.warn("Email không hợp lệ.");
            }
        });
    }

    private void openWelcome(String username, String role, Stage loginStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root = loader.load();
            WelcomeController controller = loader.getController();
            controller.setUser(username, role);

            Stage welcomeStage = new Stage();
            welcomeStage.setTitle("Chào mừng");
            welcomeStage.setScene(new Scene(root, 360, 200));
            welcomeStage.show();
            loginStage.close();
        } catch (IOException exception) {
            AlertUtil.error("Không mở được màn hình chào mừng: " + exception.getMessage());
        }
    }
}
