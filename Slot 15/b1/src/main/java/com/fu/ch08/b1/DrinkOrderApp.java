package com.fu.ch08.b1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Entry point for the Chapter 8, B1 drink-ordering application.
 */
public class DrinkOrderApp extends Application {
    private static final int TOPPING_PRICE = 5_000;
    private static final Map<String, Integer> MENU = new LinkedHashMap<>();
    static {
        MENU.put("Cà phê sữa", 25_000);
        MENU.put("Trà sữa", 30_000);
        MENU.put("Nước cam", 28_000);
        MENU.put("Sinh tố bơ", 35_000);
    }

    // Controls are fields so the event handlers and other methods can access them.
    private final TextField tfName = new TextField();
    private final ComboBox<String> cbDrink = new ComboBox<>();
    private final ToggleGroup tgSize = new ToggleGroup();
    private final RadioButton rbS = new RadioButton("S");
    private final RadioButton rbM = new RadioButton("M (+5.000)");
    private final RadioButton rbL = new RadioButton("L (+10.000)");
    private final CheckBox chkPearl = new CheckBox("Trân châu (+5.000)");
    private final CheckBox chkJelly = new CheckBox("Thạch (+5.000)");
    private final CheckBox chkLessSugar = new CheckBox("Ít đường");
    private final TextField tfQty = new TextField("1");
    private final Button btnCalc = new Button("Tính tiền");
    private final Button btnClear = new Button("Làm lại");
    private final Label lblResult = new Label("Chưa có đơn hàng");
    private final TextArea taLog = new TextArea();

    @Override
    public void start(Stage stage) {
        tfName.setPromptText("Nhập tên khách hàng");
        cbDrink.getItems().addAll(MENU.keySet());
        cbDrink.setPromptText("-- Chọn đồ uống --");
        cbDrink.setMaxWidth(Double.MAX_VALUE);

        rbS.setToggleGroup(tgSize);
        rbM.setToggleGroup(tgSize);
        rbL.setToggleGroup(tgSize);
        rbS.setUserData(0);
        rbM.setUserData(5_000);
        rbL.setUserData(10_000);
        tgSize.selectToggle(rbM);

        tfQty.setPrefColumnCount(4);
        taLog.setEditable(false);
        taLog.setPrefRowCount(8);
        taLog.setTooltip(new javafx.scene.control.Tooltip("Double-click để xóa nhật ký"));
        btnCalc.setDefaultButton(true);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new javafx.scene.control.Label("Tên khách:"), tfName);
        form.addRow(1, new javafx.scene.control.Label("Đồ uống:"), cbDrink);
        form.addRow(2, new javafx.scene.control.Label("Size:"), new HBox(15, rbS, rbM, rbL));
        form.addRow(3, new javafx.scene.control.Label("Tùy chọn:"),
                new HBox(15, chkPearl, chkJelly, chkLessSugar));
        form.addRow(4, new javafx.scene.control.Label("Số lượng:"), tfQty);

        HBox buttons = new HBox(10, btnCalc, btnClear);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        javafx.scene.control.Label title = new javafx.scene.control.Label("ĐẶT ĐỒ UỐNG");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox root = new VBox(12, title, form, buttons, lblResult,
                new javafx.scene.control.Label("Nhật ký sự kiện:"), taLog);
        root.setPadding(new Insets(16));

        javafx.scene.Scene scene = new javafx.scene.Scene(root, 540, 580);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                reset();
                log("ESC → làm lại form");
                event.consume();
            }
        });
        stage.setScene(scene);
        stage.setTitle("B1 - Đặt đồ uống");
        stage.show();
        tfName.requestFocus();
        registerEvents();
    }

    private void registerEvents() {
        // Method reference: the longer calculation logic is implemented in the next step.
        btnCalc.setOnAction(this::handleCalc);

        btnClear.setOnAction(e -> {
            reset();
            log("Bấm Làm lại");
        });

        // Anonymous EventHandler class.
        cbDrink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String drink = cbDrink.getValue();
                if (drink != null) {
                    log(String.format("Chọn đồ uống: %s (%,d đ)", drink, MENU.get(drink)));
                }
            }
        });

        tfName.setOnAction(e -> cbDrink.requestFocus());

        EventHandler<ActionEvent> onCheck = e -> {
            CheckBox source = (CheckBox) e.getSource();
            log(String.format("%s: %s", source.getText(),
                    source.isSelected() ? "chọn" : "bỏ chọn"));
        };
        chkPearl.setOnAction(onCheck);
        chkJelly.setOnAction(onCheck);
        chkLessSugar.setOnAction(onCheck);

        tgSize.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                log("Đổi size → " + ((RadioButton) newToggle).getText());
            }
        });

        tfQty.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                tfQty.setText(oldValue);
            }
        });

        // Mouse events: button hover feedback and double-click to clear the event log.
        btnCalc.setOnMouseEntered(e ->
                btnCalc.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white;"));
        btnCalc.setOnMouseExited(e -> btnCalc.setStyle(""));
        taLog.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                taLog.clear();
            }
        });
    }

    private void handleCalc(ActionEvent event) {
        String name = tfName.getText().trim();
        String drink = cbDrink.getValue();
        String qtyText = tfQty.getText();

        if (name.isEmpty()) {
            showError("Vui lòng nhập tên khách");
            tfName.requestFocus();
            return;
        }
        if (drink == null) {
            showError("Vui lòng chọn đồ uống");
            cbDrink.requestFocus();
            return;
        }
        if (tgSize.getSelectedToggle() == null) {
            showError("Vui lòng chọn size");
            return;
        }

        int quantity = qtyText.isEmpty() ? 0 : Integer.parseInt(qtyText);
        if (quantity < 1 || quantity > 20) {
            showError("Số lượng từ 1 đến 20");
            tfQty.requestFocus();
            return;
        }

        RadioButton size = (RadioButton) tgSize.getSelectedToggle();
        int unitPrice = MENU.get(drink) + (int) size.getUserData();
        if (chkPearl.isSelected()) unitPrice += TOPPING_PRICE;
        if (chkJelly.isSelected()) unitPrice += TOPPING_PRICE;

        int total = unitPrice * quantity;
        boolean discounted = quantity >= 5;
        if (discounted) total = total * 90 / 100;

        lblResult.setStyle("-fx-text-fill: #188038; -fx-font-weight: bold;");
        lblResult.setText(String.format("%s: %d × %s size %s%s → %,d đ%s",
                name, quantity, drink, size.getText().charAt(0),
                chkLessSugar.isSelected() ? " (ít đường)" : "", total,
                discounted ? " (đã giảm 10%)" : ""));
        log(String.format("Tính tiền: %,d đ", total));
    }

    private void showError(String message) {
        lblResult.setStyle("-fx-text-fill: #d93025;");
        lblResult.setText(message);
        log("Lỗi: " + message);
    }

    private void reset() {
        tfName.clear();
        cbDrink.setValue(null);
        tgSize.selectToggle(rbM);
        chkPearl.setSelected(false);
        chkJelly.setSelected(false);
        chkLessSugar.setSelected(false);
        tfQty.setText("1");
        lblResult.setStyle("");
        lblResult.setText("Chưa có đơn hàng");
        tfName.requestFocus();
    }

    private void log(String message) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        taLog.appendText(String.format("[%s] %s%n", time, message));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
