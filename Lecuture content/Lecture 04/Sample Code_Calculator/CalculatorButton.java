import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CalculatorButton {
    private final Text display;
    private final int num;
    private final Button button;

    public CalculatorButton(Text display, int num) {
        this.display = display;
        this.num = num;

        button = new Button(Integer.toString(num));
        button.setOnAction(event -> {
            display.setText(display.getText() + num);
        });
    }

    public Button getJavaFXButton() {
        return button;
    }
}
