import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalculatorInClass extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator");

        VBox calculator = new VBox(10);

        Text display = new Text();

        GridPane buttons = new GridPane();

        int row = 0;
        int col = 0;
        for (int i = 1; i <= 9; i++) {
            Button button = new Button(Integer.toString(i));
            button.setOnAction(event -> {
                display.setText(display.getText() + button.getText());
            });
            buttons.add(button, col, row);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }

        Button zero = new Button("0");
        zero.setOnAction(event -> {
            display.setText(display.getText() + zero.getText());
        });
        buttons.add(zero, 1, row+1);

        calculator.getChildren().addAll(display, buttons);

        Scene scene = new Scene(calculator);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
