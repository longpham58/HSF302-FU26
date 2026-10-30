package com.fu.ch08.b4;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

final class AlertUtil {
    private AlertUtil() {
    }

    static void info(String message) {
        show(Alert.AlertType.INFORMATION, "Thông báo", message);
    }

    static void warn(String message) {
        show(Alert.AlertType.WARNING, "Cảnh báo", message);
    }

    static void error(String message) {
        show(Alert.AlertType.ERROR, "Lỗi", message);
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
