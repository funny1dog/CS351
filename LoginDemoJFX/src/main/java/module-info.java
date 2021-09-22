module com.example.logindemojfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.logindemojfx to javafx.fxml;
    exports com.example.logindemojfx;
}