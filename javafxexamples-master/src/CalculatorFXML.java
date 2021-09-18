import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class CalculatorFXML extends Application {
    public Text display;

    public void calcButtonClicked(ActionEvent actionEvent) {
        // This...
//        if (actionEvent.getSource() instanceof Button) {
//            String buttonText = ((Button) actionEvent.getSource()).getText();
//            display.setText(display.getText() + buttonText);
//        }
        // Can be replaced with this as of Java 16! https://bugs.openjdk.java.net/browse/JDK-8250623
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

        URL location = getClass().getResource("calculatorFXML.fxml");
        FXMLLoader loader = new FXMLLoader(location);

        BorderPane root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
