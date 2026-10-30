package com.fu.ch08.b3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

/** Entry point for the Chapter 8 B3 registration exercise. */
public class RegisterApp extends Application {
    private static final String STRONG_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
    private static final String[] LEVEL_TEXT = {"Rất yếu", "Yếu", "Trung bình", "Khá", "Mạnh"};
    private static final String[] LEVEL_COLOR = {"#d93025", "#d93025", "#f29900", "#1a73e8", "#188038"};

    private final TextField tfFullName = new TextField();
    private final TextField tfEmail = new TextField();
    private final TextField tfUsername = new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final PasswordField pfConfirm = new PasswordField();
    private final ToggleGroup tgGender = new ToggleGroup();
    private final RadioButton rbMale = new RadioButton("Nam");
    private final RadioButton rbFemale = new RadioButton("Nữ");
    private final RadioButton rbOther = new RadioButton("Khác");
    private final DatePicker dpDob = new DatePicker();
    private final ComboBox<String> cbMajor = new ComboBox<>();
    private final CheckBox chkSport = new CheckBox("Thể thao");
    private final CheckBox chkMusic = new CheckBox("Âm nhạc");
    private final CheckBox chkGame = new CheckBox("Game");
    private final CheckBox chkReading = new CheckBox("Đọc sách");
    private final CheckBox chkAgree = new CheckBox("Tôi đồng ý với điều khoản sử dụng");
    private final ProgressBar pbStrength = new ProgressBar(0);
    private final Label lblStrength = new Label();
    private final Button btnRegister = new Button("Đăng ký");
    private final Button btnReset = new Button("Làm lại");
    private final Label errFullName = new Label();
    private final Label errEmail = new Label();
    private final Label errUsername = new Label();
    private final Label errPassword = new Label();
    private final Label errConfirm = new Label();
    private final Label errGender = new Label();
    private final Label errDob = new Label();
    private final Label errMajor = new Label();

    private Scene scene;
    private Parent registerView;

    @Override
    public void start(Stage stage) {
        setupControls();
        registerView = buildRegisterView();
        scene = new Scene(registerView, 580, 660);
        stage.setScene(scene);
        stage.setTitle("B3 - Đăng ký tài khoản");
        stage.show();
    }

    private void setupControls() {
        tfFullName.setPromptText("Nguyễn Văn An");
        tfEmail.setPromptText("abc@example.com");
        tfUsername.setPromptText("3–20 ký tự: a-z, 0-9, _");
        pfPassword.setPromptText("Tối thiểu 8 ký tự, có hoa, thường, số, ký tự đặc biệt");
        pfConfirm.setPromptText("Nhập lại mật khẩu");

        rbMale.setToggleGroup(tgGender);
        rbFemale.setToggleGroup(tgGender);
        rbOther.setToggleGroup(tgGender);

        dpDob.setPromptText("Chọn ngày sinh");
        dpDob.setEditable(false);
        dpDob.setMaxWidth(Double.MAX_VALUE);

        cbMajor.getItems().addAll("Kỹ thuật phần mềm", "Trí tuệ nhân tạo",
                "An toàn thông tin", "Thiết kế đồ họa");
        cbMajor.setPromptText("-- Chọn ngành --");
        cbMajor.setMaxWidth(Double.MAX_VALUE);

        pbStrength.setPrefWidth(140);
        Stream.of(errFullName, errEmail, errUsername, errPassword, errConfirm,
                errGender, errDob, errMajor).forEach(error -> {
            error.setStyle("-fx-text-fill: #d93025; -fx-font-size: 11;");
            error.setWrapText(true);
        });
        btnRegister.disableProperty().bind(chkAgree.selectedProperty().not());
        btnRegister.setDefaultButton(true);
    }

    private Parent buildRegisterView() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(6);
        int row = 0;
        addField(grid, row++, "Họ tên *", tfFullName, errFullName);
        addField(grid, row++, "Email *", tfEmail, errEmail);
        addField(grid, row++, "Tên đăng nhập *", tfUsername, errUsername);
        HBox strength = new HBox(8, pbStrength, lblStrength);
        strength.setAlignment(Pos.CENTER_LEFT);
        addField(grid, row++, "Mật khẩu *", new VBox(4, pfPassword, strength), errPassword);
        addField(grid, row++, "Xác nhận mật khẩu *", pfConfirm, errConfirm);
        addField(grid, row++, "Giới tính *", new HBox(15, rbMale, rbFemale, rbOther), errGender);
        addField(grid, row++, "Ngày sinh *", dpDob, errDob);
        addField(grid, row++, "Ngành *", cbMajor, errMajor);
        addField(grid, row++, "Sở thích",
                new FlowPane(12, 6, chkSport, chkMusic, chkGame, chkReading), null);

        ColumnConstraints labelColumn = new ColumnConstraints(110);
        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelColumn, inputColumn);

        Label title = new Label("ĐĂNG KÝ TÀI KHOẢN");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #1a73e8;");
        HBox actions = new HBox(10, btnRegister, btnReset);
        actions.setAlignment(Pos.CENTER_RIGHT);
        Hyperlink linkLogin = new Hyperlink("Đã có tài khoản? Đăng nhập");

        VBox form = new VBox(12, title, grid, chkAgree, actions, linkLogin);
        form.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane(form);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private void addField(GridPane grid, int row, String label, Node input, Label error) {
        Label fieldLabel = new Label(label);
        fieldLabel.setPadding(new Insets(4, 0, 0, 0));
        GridPane.setValignment(fieldLabel, VPos.TOP);
        grid.add(fieldLabel, 0, row);
        grid.add(error == null ? input : new VBox(2, input, error), 1, row);
    }

    private boolean check(Label error, boolean valid, String message) {
        error.setText(valid ? "" : message);
        return valid;
    }

    private boolean vFullName() {
        return check(errFullName,
                tfFullName.getText().trim().matches("[\\p{L}\\p{M} ]{3,50}"),
                "Họ tên phải có 3–50 ký tự, chỉ gồm chữ cái và khoảng trắng.");
    }

    private boolean vEmail() {
        return check(errEmail,
                tfEmail.getText().trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"),
                "Email không hợp lệ (ví dụ: abc@example.com).");
    }

    private boolean vUsername() {
        String username = tfUsername.getText().trim().toLowerCase();
        if (!username.matches("[a-z0-9_]{3,20}")) {
            return check(errUsername, false,
                    "Tên đăng nhập phải có 3–20 ký tự: chữ thường, số hoặc dấu gạch dưới.");
        }
        return check(errUsername, !UserStore.exists(username), "Tên đăng nhập đã tồn tại.");
    }

    private boolean vPassword() {
        return check(errPassword, pfPassword.getText().matches(STRONG_PASSWORD),
                "Mật khẩu cần ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
    }

    private boolean vConfirm() {
        String confirmation = pfConfirm.getText();
        return check(errConfirm, !confirmation.isEmpty() && confirmation.equals(pfPassword.getText()),
                "Mật khẩu xác nhận không khớp.");
    }

    private boolean vGender() {
        return check(errGender, tgGender.getSelectedToggle() != null, "Vui lòng chọn giới tính.");
    }

    private boolean vDob() {
        LocalDate date = dpDob.getValue();
        if (date == null) return check(errDob, false, "Vui lòng chọn ngày sinh.");
        if (date.isAfter(LocalDate.now())) {
            return check(errDob, false, "Ngày sinh không được ở tương lai.");
        }
        return check(errDob, Period.between(date, LocalDate.now()).getYears() >= 16,
                "Bạn phải đủ 16 tuổi trở lên.");
    }

    private boolean vMajor() {
        return check(errMajor, cbMajor.getValue() != null, "Vui lòng chọn ngành.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
