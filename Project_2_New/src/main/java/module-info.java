module com.example.project_2_new {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_2_new to javafx.fxml;
    exports com.example.project_2_new;
}