package com.fu.ch08.b3;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** Small alert helpers used by the B3 exercise. */
public final class AlertUtil {
    private AlertUtil() {
    }

    public static void info(String message) {
        show(Alert.AlertType.INFORMATION, "Thông báo", message);
    }

    public static void warn(String message) {
        show(Alert.AlertType.WARNING, "Cảnh báo", message);
    }

    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message,
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
