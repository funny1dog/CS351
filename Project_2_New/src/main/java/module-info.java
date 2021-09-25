module com.example.project_2_new {
    requires javafx.controls;
    requires javafx.fxml;


    opens HumanBenchmark to javafx.fxml;
    exports HumanBenchmark;
}