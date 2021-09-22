module com.example.multiplecontrollertest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multiplecontrollertest to javafx.fxml;
    exports com.example.multiplecontrollertest;
}