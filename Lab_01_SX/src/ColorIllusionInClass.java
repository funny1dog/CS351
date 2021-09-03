import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColorIllusionInClass extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Color Illusion In Class");

        double size = 500;
        int insideR = 255;
        int insideG = 255;
        int insideB = 255;
        int outsideR = 255;
        int outsideG = 0;
        int outsideB = 0;
        int backgroundR = 0;
        int backgroundG = 255;
        int backgroundB = 0;

        TextField insideRInput = new TextField();
        insideRInput.setLayoutX(140);
        insideRInput.setLayoutY(25);
        insideRInput.setMaxWidth(60);
        insideRInput.setPromptText("1-255");
        insideRInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                insideRInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField insideGInput = new TextField();
        insideGInput.setLayoutX(210);
        insideGInput.setLayoutY(25);
        insideGInput.setMaxWidth(60);
        insideGInput.setPromptText("1-255");
        insideGInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                insideGInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField insideBInput = new TextField();
        insideBInput.setLayoutX(280);
        insideBInput.setLayoutY(25);
        insideBInput.setMaxWidth(60);
        insideBInput.setPromptText("1-255");
        insideBInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                insideBInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        TextField outsideRInput = new TextField();
        outsideRInput.setLayoutX(140);
        outsideRInput.setLayoutY(50);
        outsideRInput.setMaxWidth(60);
        outsideRInput.setPromptText("1-255");
        outsideRInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                outsideRInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField outsideGInput = new TextField();
        outsideGInput.setLayoutX(210);
        outsideGInput.setLayoutY(50);
        outsideGInput.setMaxWidth(60);
        outsideGInput.setPromptText("1-255");
        outsideGInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                outsideGInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField outsideBInput = new TextField();
        outsideBInput.setLayoutX(280);
        outsideBInput.setLayoutY(50);
        outsideBInput.setMaxWidth(60);
        outsideBInput.setPromptText("1-255");
        outsideBInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                outsideBInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField backgroundRInput = new TextField();
        backgroundRInput.setLayoutX(140);
        backgroundRInput.setLayoutY(75);
        backgroundRInput.setMaxWidth(60);
        backgroundRInput.setPromptText("1-255");
        backgroundRInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                backgroundRInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField backgroundGInput = new TextField();
        backgroundGInput.setLayoutX(210);
        backgroundGInput.setLayoutY(75);
        backgroundGInput.setMaxWidth(60);
        backgroundGInput.setPromptText("1-255");
        backgroundGInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                backgroundGInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField backgroundBInput = new TextField();
        backgroundBInput.setLayoutX(280);
        backgroundBInput.setLayoutY(75);
        backgroundBInput.setMaxWidth(60);
        backgroundBInput.setPromptText("1-255");
        backgroundBInput.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                backgroundBInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextField circleInsideRadius = new TextField();
        circleInsideRadius.setLayoutX(140);
        circleInsideRadius.setLayoutY(100);
        circleInsideRadius.setMaxWidth(60);
        circleInsideRadius.setPromptText("1-250");
        circleInsideRadius.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                circleInsideRadius.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        TextField circleOutsideRadius = new TextField();
        circleOutsideRadius.setLayoutX(140);
        circleOutsideRadius.setLayoutY(125);
        circleOutsideRadius.setMaxWidth(60);
        circleOutsideRadius.setPromptText("1-250");
        circleOutsideRadius.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                circleOutsideRadius.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });



        Pane root = new Pane();

        Circle outer = new Circle();
        outer.setCenterX(size / 2);
        outer.setCenterY(size / 2);
        outer.setRadius(size / 5);
        outer.setFill(Color.rgb(outsideR, outsideG, outsideB));

        Circle inner = new Circle();
        inner.setCenterX(size / 2);
        inner.setCenterY(size / 2);
        inner.setRadius(size / 100);
        inner.setFill(Color.rgb(insideR, insideG, insideB));

        Rectangle backgroundRect = new Rectangle(size, size, Color.rgb(backgroundR,backgroundG,backgroundB));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                outer.setRadius(outer.getRadius()-0.05);
            }
        };

        Button button = new Button("Start");
        button.setOnAction(event -> {
            System.out.println("event = " + event);
            timer.start();
        });

        Button changeInsideColorButton = new Button("Change Inside Color");
        changeInsideColorButton.setOnAction(event -> {
            inner.setFill(Color.rgb(Integer.parseInt(insideRInput.getText()),Integer.parseInt(insideGInput.getText()),Integer.parseInt(insideBInput.getText())));
        });
        changeInsideColorButton.setLayoutX(0);
        changeInsideColorButton.setLayoutY(25);

        Button changeOutsideColorButton = new Button("Change Outside Color");
        changeOutsideColorButton.setOnAction(event -> {
            outer.setFill(Color.rgb(Integer.parseInt(outsideRInput.getText()),Integer.parseInt(outsideGInput.getText()),Integer.parseInt(outsideBInput.getText())));
        });
        changeOutsideColorButton.setLayoutX(0);
        changeOutsideColorButton.setLayoutY(50);

        Button changeBackgroundColorButton = new Button("Change BG Color");
        changeBackgroundColorButton.setOnAction(event -> {
            backgroundRect.setFill(Color.rgb(Integer.parseInt(backgroundRInput.getText()),Integer.parseInt(backgroundGInput.getText()),Integer.parseInt(backgroundBInput.getText())));
        });
        changeBackgroundColorButton.setLayoutX(0);
        changeBackgroundColorButton.setLayoutY(75);

        Button changeInsideCircleRadiusButton = new Button("Change Inside Radius");
        changeInsideCircleRadiusButton.setOnAction(event -> {
            inner.setRadius(Integer.parseInt(circleInsideRadius.getText()));

        });
        changeInsideCircleRadiusButton.setLayoutX(0);
        changeInsideCircleRadiusButton.setLayoutY(100);



        Button changeOutsideCircleRadiusButton = new Button("Change Outside Radius");
        changeOutsideCircleRadiusButton.setOnAction(event -> {
            outer.setRadius(Integer.parseInt(circleOutsideRadius.getText()));

        });
        changeOutsideCircleRadiusButton.setLayoutX(0);
        changeOutsideCircleRadiusButton.setLayoutY(125);



        root.getChildren().addAll(backgroundRect, outer, inner, button,
                changeInsideColorButton, insideRInput, insideGInput, insideBInput,
                changeOutsideColorButton, outsideGInput, outsideBInput, outsideRInput,
                changeBackgroundColorButton, backgroundRInput, backgroundGInput, backgroundBInput,
                changeInsideCircleRadiusButton, circleInsideRadius,
                changeOutsideCircleRadiusButton, circleOutsideRadius);

        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
