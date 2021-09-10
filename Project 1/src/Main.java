import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.Random;

public class Main extends Application {
    // set up basic variables
    private Color color;
    private double numOfIncrement = 0.1;
    private double numOfFactor = 0;
    private double frameRate = 0;

    // sort decimal format into the right format
    private DecimalFormat decimalFormat = new DecimalFormat ("#.##");

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // create canvas and element space size
        double Width = 525;
        double Height = 800;
        double Spacing = 5;

        // initialize canvas, pane, stage and scenes;
        primaryStage.setTitle("Modulo Times Tables Visualization ");
        Canvas canvas1 = new Canvas (Width, Height);
        Pane pane1 = new Pane (canvas1);
        Scene scene1 = new Scene (pane1);
        primaryStage.setScene(scene1);
        primaryStage.show();

        // create the circle and pass the circle into the main part
        Calculation circle = new Calculation();

        // create slider control to set increments elements and two vbox
        VBox sliderIncrementVBox = new VBox(Spacing);
        Label lblIncrement = new Label("Increment: " + decimalFormat.format(numOfIncrement));
        Slider sliderIncrement = new Slider (0, 5,0.1);
        sliderIncrement.setBlockIncrement (0.1);
        sliderIncrement.setMajorTickUnit (0.25);
        sliderIncrement.setShowTickLabels (true);
        sliderIncrement.setShowTickMarks(true);
        sliderIncrement.setPrefWidth(250);
        sliderIncrementVBox.getChildren().addAll(lblIncrement, sliderIncrement);

        // listener of the increment slider
        sliderIncrement.valueProperty().addListener((observable, oldValue, newValue) -> {
            numOfIncrement = sliderIncrement.getValue();
            lblIncrement.setText("Increment: " + decimalFormat.format(numOfIncrement));
        });

        // create slider control to set fps
        VBox sliderFPSBox = new VBox(Spacing);
        Label lblFPS = new Label("Frame Rate: " + decimalFormat.format(frameRate));
        Slider sliderFPS = new Slider (0, 0.5, 0.05);
        sliderFPS.setShowTickLabels(true);
        sliderFPS.setShowTickMarks((true));
        sliderFPS.setBlockIncrement(0.01);
        sliderFPS.setMajorTickUnit(0.05);
        sliderFPS.setPrefWidth(250);
        sliderFPSBox.getChildren().addAll(lblFPS, sliderFPS);

        // listener of the fps/frame rate
        sliderFPS.valueProperty().addListener((observable, oldValue, newValue) -> {
            frameRate = sliderFPS.getValue();
            lblFPS.setText("Frame Rate: " + decimalFormat.format(frameRate));
        });

        // combine two VBox into one HBox
        HBox HboxTop = new HBox (Spacing);
        HboxTop.getChildren().addAll(sliderIncrementVBox, sliderFPSBox);
        HboxTop.setLayoutX(10);
        HboxTop.setLayoutY(500);

        // create slider control to set up # of points and put into VBox;
        VBox HboxPointSlider = new VBox (Spacing);
        Label lblPoints = new Label("Number of Points: " + circle.getNumOfPoints());
        Slider sliderPoints = new Slider(0,360, 60);
        sliderPoints.setShowTickLabels(true);
        sliderPoints.setShowTickMarks(true);
        sliderPoints.setBlockIncrement(1);
        sliderPoints.setMajorTickUnit(20);
        sliderPoints.setPrefWidth(500);
        HboxPointSlider.getChildren().addAll(lblPoints, sliderPoints);
        HboxPointSlider.setLayoutX(10);
        HboxPointSlider.setLayoutY(570);

        // listener of the point number setter
        sliderPoints.valueProperty().addListener((observable, oldValue, newValue) -> {
            circle.setNumOfPoints((int)sliderPoints.getValue());
            lblPoints.setText("Number of Points: " + circle.getNumOfPoints());
        });

        // create control to jump elements
        TextField boxSetNumOfPoint = new TextField();
        boxSetNumOfPoint.setPromptText("1-360");
        Label lblSetNumOfPoint = new Label ("Number of Points: ");
        TextField boxSetNumOfFactor = new TextField();
        boxSetNumOfFactor.setPromptText("1-360");
        Label lblsetNumbOfFactor = new Label ("Target arget time table");
        Button btnJump = new Button ("Jump");
        Label lblBtnJump = new Label ("");

        // getting jump control to a box
        VBox VboxJumpControl1 = new VBox (Spacing);
        VboxJumpControl1.getChildren().addAll(lblSetNumOfPoint,boxSetNumOfPoint);
        VBox VboxJumpControl2 = new VBox (Spacing);
        VboxJumpControl2.getChildren().addAll(lblsetNumbOfFactor,boxSetNumOfFactor);
        VBox VboxJumpControl3 = new VBox (Spacing);
        VboxJumpControl3.getChildren().addAll(lblBtnJump, btnJump);
        HBox HboxJumpControl = new HBox (20);
        HboxJumpControl.getChildren().addAll(VboxJumpControl1, VboxJumpControl2, VboxJumpControl3);
        HboxJumpControl.setLayoutX(10);
        HboxJumpControl.setLayoutY(640);

        // set point box listener
        boxSetNumOfPoint.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                boxSetNumOfPoint.setText(newValue.replaceAll("[^\\d]", ""));
            }
        } );

        // set factor box listener
        boxSetNumOfFactor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                boxSetNumOfFactor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        } );

        // create start, stop, random and put them in hbox
        Button btnStart = new Button ("Start");
        Button btnStop = new Button ("Stop");
        Button btnRandom = new Button ("Generate Random Pictures");
        HBox HboxBtn = new HBox (Spacing);
        HboxBtn.getChildren().addAll(btnStart, btnStop, btnRandom);
        HboxBtn.setLayoutX(10);
        HboxBtn.setLayoutY(710);

        // set up animation
        AnimationTimer timer = new AnimationTimer() {
            private long nextTime = 0;
            @Override
            public void handle(long now) {
                if (now - nextTime >= 1000_000_000 * sliderFPS.getValue()){
                    numOfFactor = numOfFactor + numOfIncrement;
                    int randomColor;
                    randomColor = (int)(numOfFactor % 50);

                    // random color
                    switch(randomColor){
                        default: color = Color.color(Math.random(), Math.random(), Math.random());
                    }

                    // draw the line with random color
                    Calculation drawCircle = new Calculation(numOfFactor, circle.getNumOfPoints(), color);
                    drawCircle.show(pane1);
                    nextTime = now;
                }

            }
        };

        // start listener
        btnStart.setOnAction(event -> {
            pane1.getChildren().clear();
            sliderPoints.setValue(circle.getNumOfPoints());
            timer.start();
            pane1.getChildren().addAll(HboxTop, HboxPointSlider, HboxJumpControl, HboxBtn);
        });

        // stop listener
        btnStop.setOnAction(event -> {
            timer.stop();
        });

        // random listener
        btnRandom.setOnAction(event -> {
            timer.stop();
            pane1.getChildren().clear();
            Random random = new Random();
            int randomFactor = random.nextInt(360);
            int randomPoint = random.nextInt(360);
            generateCircle(randomFactor, randomPoint, Color.color(Math.random(), Math.random(), Math.random()), pane1);
            pane1.getChildren().addAll(HboxTop, HboxPointSlider, HboxJumpControl, HboxBtn);
        });

        // set btnJump listener
        btnJump.setOnAction(event -> {
            timer.stop();
            pane1.getChildren().clear();

            generateCircle(Integer.parseInt(boxSetNumOfFactor.getText()),
                    Integer.parseInt(boxSetNumOfPoint.getText()),
                    Color.color(Math.random(), Math.random(), Math.random()), pane1);

            circle.setNumOfPoints(Integer.parseInt(boxSetNumOfPoint.getText()));
            numOfFactor = Integer.parseInt(boxSetNumOfFactor.getText());

            sliderPoints.setValue(Integer.parseInt(boxSetNumOfPoint.getText()));

            pane1.getChildren().addAll(HboxTop, HboxPointSlider, HboxJumpControl, HboxBtn);
        });

        // update pane
        pane1.getChildren().addAll(HboxTop, HboxPointSlider, HboxJumpControl, HboxBtn);
    }
    public void generateCircle (int numOfFactor, int numberOfPoints, Color color, Pane pane){
        Calculation circle = new Calculation(numOfFactor, numberOfPoints, color);
        circle.show(pane);
    }
}
