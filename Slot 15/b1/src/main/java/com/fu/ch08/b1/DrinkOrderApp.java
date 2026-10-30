package com.fu.ch08.b1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Entry point for the Chapter 8, B1 drink-ordering application.
 * UI setup will be added in the following steps.
 */
public class DrinkOrderApp extends Application {
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
        stage.setScene(scene);
        stage.setTitle("B1 - Đặt đồ uống");
        stage.show();
        tfName.requestFocus();
        registerEvents();
    }

    private void registerEvents() {
        // Method reference: the longer calculation logic is implemented in the next step.
        btnCalc.setOnAction(this::handleCalc);

        // Lambda handler shared by the reset button for now; reset behavior follows in step 5.
        btnClear.setOnAction(e -> log("Bấm Làm lại"));

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
    }

    private void handleCalc(ActionEvent event) {
        log("Bấm Tính tiền; phần tính toán sẽ được thêm ở Bước 5");
    }

    private void log(String message) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        taLog.appendText(String.format("[%s] %s%n", time, message));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
