package com.example.ui.controller;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class StudentController {

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TextField searchField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField ageField;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Label messageLabel;
    @FXML
    private Label statusLabel;

    private final StudentService studentService;
    private final ObservableList<Student> data = FXCollections.observableArrayList();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @FXML
    public void initialize() {
        tableView.setItems(data);
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillForm(newSelection);
                    }
                });

        var noSelection = tableView.getSelectionModel().selectedItemProperty().isNull();
        btnUpdate.disableProperty().bind(noSelection);
        btnDelete.disableProperty().bind(noSelection);
        loadData();
    }

    private void loadData() {
        try {
            data.setAll(studentService.findAll());
            statusLabel.setText("Loaded " + data.size() + " students");
        } catch (Exception e) {
            showError("Cannot load data: " + e.getMessage());
            statusLabel.setText("Database error");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            data.setAll(studentService.search(searchField.getText()));
            statusLabel.setText("Found " + data.size() + " students");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadData();
    }

    private Student readForm() {
        String ageText = ageField.getText() == null ? "" : ageField.getText().trim();
        if (ageText.isEmpty()) {
            throw new ValidationException("Age is required");
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            throw new ValidationException("Age must be a number");
        }
        return new Student(nameField.getText(), emailField.getText(), age);
    }

    @FXML
    private void handleAdd() {
        try {
            Student saved = studentService.create(readForm());
            showInfo("Added: " + saved.getName());
            handleClear();
            loadData();
        } catch (ValidationException | StudentNotFoundException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("System error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a student");
            return;
        }

        try {
            Student edit = readForm();
            edit.setId(selected.getId());
            Student updated = studentService.update(edit);
            showInfo("Updated: " + updated.getName());
            handleClear();
            loadData();
        } catch (ValidationException | StudentNotFoundException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("System error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a student");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete student \"" + selected.getName() + "\"?",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Confirm delete");
        confirm.initOwner(tableView.getScene().getWindow());
        confirm.showAndWait()
                .filter(result -> result == ButtonType.OK)
                .ifPresent(result -> {
                    try {
                        studentService.delete(selected.getId());
                        showInfo("Deleted: " + selected.getName());
                        handleClear();
                        loadData();
                    } catch (StudentNotFoundException e) {
                        showError(e.getMessage());
                        loadData();
                    } catch (Exception e) {
                        showError("System error: " + e.getMessage());
                    }
                });
    }

    @FXML
    private void handleClear() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        ageField.clear();
        tableView.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    private void fillForm(Student student) {
        idField.setText(String.valueOf(student.getId()));
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        ageField.setText(String.valueOf(student.getAge()));
    }

    private void showInfo(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: green;");
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }
}
