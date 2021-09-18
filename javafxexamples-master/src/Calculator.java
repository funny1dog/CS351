import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator");

        BorderPane calculator = new BorderPane();
        Text display = new Text();
        GridPane buttons = new GridPane();

        int row = 0;
        int col = 0;
        for (int i = 1; i <= 9; i++) {
            buttons.add(new CalculatorButton(display, i).getJavaFXButton(), col, row);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
        buttons.add(new CalculatorButton(display, 0).getJavaFXButton(),
                1, row+1, 3, 1);

        calculator.setTop(display);
        calculator.setCenter(buttons);

        Scene scene = new Scene(calculator);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
