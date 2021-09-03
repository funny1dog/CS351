import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        // initiate canvas and title
        primaryStage.setTitle("Color Illusion In Class");
        double size = 600;
        Pane root = new Pane();

        // initiate outer circle
        Circle outer = new Circle();
        outer.setCenterX(size / 2);
        outer.setCenterY(size / 2);
        outer.setRadius(size / 5);
        outer.setFill(Color.RED);

        // initiate inner circle
        Circle inner = new Circle();
        inner.setCenterX(size / 2);
        inner.setCenterY(size / 2);
        inner.setRadius(size / 100);
        inner.setFill(Color.WHITE);

        // initiate background
        Rectangle backgroundRect = new Rectangle(size, size, Color.GREEN);

        // initiate animation timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                outer.setRadius(outer.getRadius()-0.05);
            }
        };

        // initiate the start button and set action
        Button button = new Button("Start");
        button.setLayoutX (10);
        button.setLayoutY (345);
        button.setOnAction(event -> {
            System.out.println("event = " + event);
            timer.start();
        });

        // control to set up inner circle size
        TextField innerRadiusInput = new TextField();
        innerRadiusInput.setLayoutX (175);
        innerRadiusInput.setLayoutY (380);
        innerRadiusInput.setMaxWidth (80);
        innerRadiusInput.textProperty().addListener((observable, oldValue, newValue) -> {
           while (!newValue.matches("\\d*")) {
               //pop out window
           }
        });

        // Button to set up inner circle size button
        Button changeInnerCircle = new Button("Change inner circle Radius");
        changeInnerCircle.setLayoutX (10);
        changeInnerCircle.setLayoutY (380);
        changeInnerCircle.setOnAction(event -> {
            inner.setRadius(Integer.parseInt(innerRadiusInput.getText()));
                });

        // control to set up outer circle size
        TextField outerRadiusInput = new TextField();
        outerRadiusInput.setLayoutX (175);
        outerRadiusInput.setLayoutY (415);
        outerRadiusInput.setMaxWidth (80);
        outerRadiusInput.textProperty().addListener((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        });

        // Button to set up outer circle size button
        Button changeOuterCircile = new Button("Change outer circle Radius");
        changeOuterCircile.setLayoutX (10);
        changeOuterCircile.setLayoutY (415);
        changeOuterCircile.setOnAction(event -> {
            outer.setRadius(Integer.parseInt(outerRadiusInput.getText()));
        });

        // work on inner, outer, bb color textfiled
        // iR
        TextField iRinput = new TextField();
        iRinput.setLayoutX(180);
        iRinput.setLayoutY(450);
        iRinput.setMaxWidth(40);

        iRinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
            }
        }));

        // iG
        TextField iGinput = new TextField();
        iGinput.setLayoutX(230);
        iGinput.setLayoutY(450);
        iGinput.setMaxWidth(40);

        iGinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // iB
        TextField iBinput = new TextField();
        iBinput.setLayoutX(280);
        iBinput.setLayoutY(450);
        iBinput.setMaxWidth(40);

        iBinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // oR
        TextField oRinput = new TextField();
        oRinput.setLayoutX(180);
        oRinput.setLayoutY(485);
        oRinput.setMaxWidth(40);

        oRinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // oG
        TextField oGinput = new TextField();
        oGinput.setLayoutX(230);
        oGinput.setLayoutY(485);
        oGinput.setMaxWidth(40);

        oGinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // oB
        TextField oBinput = new TextField();
        oBinput.setLayoutX(280);
        oBinput.setLayoutY(485);
        oBinput.setMaxWidth(40);

        oBinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // bR
        TextField bRinput = new TextField();
        bRinput.setLayoutX(180);
        bRinput.setLayoutY(520);
        bRinput.setMaxWidth(40);

        bRinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // bG
        TextField bGinput = new TextField();
        bGinput.setLayoutX(230);
        bGinput.setLayoutY(520);
        bGinput.setMaxWidth(40);

        bGinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // bB
        TextField bBinput = new TextField();
        bBinput.setLayoutX(280);
        bBinput.setLayoutY(520);
        bBinput.setMaxWidth(40);

        bBinput.textProperty().addListener(((observable, oldValue, newValue) -> {
            while (!newValue.matches("\\d*")) {
                //pop out window
            }
        }));

        // buttons of changing RGBs
        Button changeInnerRGB = new Button ("Change Inner Circle Color");
        changeInnerRGB.setLayoutX(10);
        changeInnerRGB.setLayoutY(450);
        changeInnerRGB.setOnAction(event -> {
           inner.setFill(Color.rgb(Integer.parseInt(iRinput.getText()), Integer.parseInt(iGinput.getText()),Integer.parseInt(iBinput.getText())));
        });

        Button changeOuterRGB = new Button ("Change Outer Circle Color");
        changeOuterRGB.setLayoutX(10);
        changeOuterRGB.setLayoutY(485);
        changeOuterRGB.setOnAction(event -> {
            outer.setFill(Color.rgb(Integer.parseInt(oRinput.getText()), Integer.parseInt(oGinput.getText()),Integer.parseInt(oBinput.getText())));
        });

        Button changeBackgroundRGB = new Button ("Change Background Color");
        changeBackgroundRGB.setLayoutX(10);
        changeBackgroundRGB.setLayoutY(520);
        changeBackgroundRGB.setOnAction(event -> {
            backgroundRect.setFill(Color.rgb(Integer.parseInt(bRinput.getText()), Integer.parseInt(bGinput.getText()),Integer.parseInt(bBinput.getText())));
        });


        // implant all elements onto the scene
        root.getChildren().addAll(backgroundRect, outer, inner, button,
                changeInnerCircle, innerRadiusInput, changeOuterCircile, outerRadiusInput,
                iRinput, iGinput, iBinput,
                oRinput, oGinput, oBinput,
                bRinput, bGinput, bBinput,
                changeInnerRGB, changeOuterRGB,changeBackgroundRGB
        );
        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
