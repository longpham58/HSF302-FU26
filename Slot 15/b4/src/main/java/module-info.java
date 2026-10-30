module com.fu.ch08.b4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fu.ch08.b4 to javafx.fxml;
    exports com.fu.ch08.b4;
}
