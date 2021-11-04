
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Weather App");

        VBox controls = new VBox(10);

        HBox tempDisplay = new HBox(10);
        Label tempLabel = new Label("Temperature: ");
        Label tempValueLabel = new Label();
        tempDisplay.getChildren().addAll(tempLabel, tempValueLabel);

        HBox humidityDisplay = new HBox(10);
        Label humidityLabel = new Label("Humidity: ");
        Label humidityValueLabel = new Label();
        humidityDisplay.getChildren().addAll(humidityLabel, humidityValueLabel);

        Button changeValues =  new Button("Change Weather");

        controls.getChildren().addAll(tempDisplay, humidityDisplay, changeValues);

        Pane root = new Pane(controls);
        Scene scene = new Scene(root, 450, 450);

//        WeatherData weatherData = new WeatherData(
//                75,
//                21,
//                30.21f);
//        CurrentConditionsDisplay ccd =
//                new CurrentConditionsDisplay(
//                        weatherData,
//                        tempValueLabel,
//                        humidityValueLabel);

        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay ccd =
                new CurrentConditionsDisplay(weatherData, tempValueLabel, humidityValueLabel);

        weatherData.setTemperature(75);
        weatherData.setHumidity(21);
        weatherData.setPressure(30.21f);

        changeValues.setOnAction(event -> {
            weatherData.setTemperature(ThreadLocalRandom.current().nextDouble(100));
            weatherData.setHumidity(ThreadLocalRandom.current().nextDouble(100));
            weatherData.setPressure(ThreadLocalRandom.current().nextDouble(100));
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
