
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

//public class CurrentConditionsDisplay implements MyObserver, DisplayElement {
//    private double temperature;
//    private double humidity;
//
//    private final Label tempLabel;
//    private final Label humidityLabel;
//
//    public CurrentConditionsDisplay(Subject weatherData,
//                                    Label tempLabel,
//                                    Label humidityLabel) {
//        weatherData.registerObserver(this);
//
//        this.tempLabel = tempLabel;
//        this.humidityLabel = humidityLabel;
//    }
//
//    @Override
//    public void display() {
//        tempLabel.setText(Double.toString(temperature));
//        humidityLabel.setText(Double.toString(humidity));
//    }
//
//    @Override
//    public void update(double temp, double humidity, double pressure) {
//        this.temperature = temp;
//        this.humidity = humidity;
//        display();
//    }
//}

//public class CurrentConditionsDisplay implements Observer, DisplayElement {
//    private double temperature;
//    private double humidity;
//
//    private Label tempLabel;
//    private Label humidityLabel;
//
//    public CurrentConditionsDisplay(Observable weatherData,
//                                    Label tempLabel,
//                                    Label humidityLabel) {
//        weatherData.addObserver(this);
//
//        this.tempLabel = tempLabel;
//        this.humidityLabel = humidityLabel;
//    }
//
//    @Override
//    public void display() {
//        tempLabel.setText(Double.toString(temperature));
//        humidityLabel.setText(Double.toString(humidity));
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if (arg instanceof WeatherData wd) {
//            this.temperature = wd.getTemperature();
//            this.humidity = wd.getHumidity();
//            display();
//        }
//    }
//}

public class CurrentConditionsDisplay
        implements PropertyChangeListener, DisplayElement {
    private double temperature;
    private double humidity;

    private final Label tempLabel;
    private final Label humidityLabel;

    public CurrentConditionsDisplay(WeatherData weatherData,
                                    Label tempLabel,
                                    Label humidityLabel) {
        weatherData.addPropertyChangeListener(this);

        this.tempLabel = tempLabel;
        this.humidityLabel = humidityLabel;
    }

    @Override
    public void display() {
        tempLabel.setText(Double.toString(temperature));
        humidityLabel.setText(Double.toString(humidity));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("temperature")) {
            this.temperature = (double) evt.getNewValue();
            display();
        }
        else if (evt.getPropertyName().equals("humidity")) {
            this.humidity = (double) evt.getNewValue();
            display();
        }
    }
}