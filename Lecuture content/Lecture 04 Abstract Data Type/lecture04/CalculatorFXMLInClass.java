import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class CalculatorFXMLInClass extends Application {
    public GridPane buttons;
    @FXML
    private Text display;

    public void initialize() {
        // loop here
    }

    public void calcButtonClicked(ActionEvent actionEvent) {
//        if (actionEvent.getSource() instanceof Button) {
//            String buttonText = ((Button) actionEvent.getSource()).getText();
//        }
        if (actionEvent.getSource() instanceof Button b) {
            display.setText(display.getText() + b.getText());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator FXML");

        URL location = getClass().getResource("calculatorFXMLInClass.fxml");
        FXMLLoader loader = new FXMLLoader(location);

        BorderPane root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
