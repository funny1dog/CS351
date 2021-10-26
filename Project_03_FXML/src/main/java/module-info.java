module scrabble.project_03_fxml {
    requires javafx.controls;
    requires javafx.fxml;


    opens scrabble to javafx.fxml;
    exports scrabble;
}