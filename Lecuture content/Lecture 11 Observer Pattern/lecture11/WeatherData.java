
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

//public class WeatherData implements Subject {
//    private List<MyObserver> observers;
//    private double temperature;
//    private double humidity;
//    private double pressure;
//
//    public WeatherData(double temperature, double humidity, double pressure) {
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.pressure = pressure;
//        observers = new ArrayList<>();
//    }
//
//    public double getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//        measurementsChanged();
//    }
//
//    public double getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(double humidity) {
//        this.humidity = humidity;
//        measurementsChanged();
//    }
//
//    public double getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(double pressure) {
//        this.pressure = pressure;
//        measurementsChanged();
//    }
//
//    public void measurementsChanged() {
//        notifyObservers();
//    }
//
//    @Override
//    public void registerObserver(MyObserver o) {
//        observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(MyObserver o) {
//        observers.remove(o);
//    }
//
//    @Override
//    public void notifyObservers() {
//        for (MyObserver observer : observers) {
//            observer.update(temperature, humidity, pressure);
//        }
//    }
//}

//public class WeatherData extends Observable {
//    private double temperature;
//    private double humidity;
//    private double pressure;
//
//    public WeatherData(double temperature, double humidity, double pressure) {
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.pressure = pressure;
//    }
//
//    public double getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//        measurementsChanged();
//    }
//
//    public double getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(double humidity) {
//        this.humidity = humidity;
//        measurementsChanged();
//    }
//
//    public double getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(double pressure) {
//        this.pressure = pressure;
//        measurementsChanged();
//    }
//
//    public void measurementsChanged() {
//        setChanged();
//        notifyObservers(this);
//    }
//}

public class WeatherData {
    private double temperature = 0;
    private double humidity = 0;
    private double pressure = 0;
    private double windSpeed = 0;

    private final PropertyChangeSupport support;

    public WeatherData() {
        this.support = new PropertyChangeSupport(this);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        double oldTemperature = this.temperature;
        this.temperature = temperature;
        this.support.firePropertyChange("temperature", oldTemperature, temperature);
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        double oldHumidity = this.humidity;
        this.humidity = humidity;
        this.support.firePropertyChange("humidity", oldHumidity, humidity);
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        double oldPressure = this.pressure;
        this.pressure = pressure;
        this.support.firePropertyChange("pressure", oldPressure, pressure);
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        double oldWindSpeed = this.windSpeed;
        this.windSpeed = windSpeed;
        this.support.firePropertyChange("windSpeed", oldWindSpeed, windSpeed);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }
}
