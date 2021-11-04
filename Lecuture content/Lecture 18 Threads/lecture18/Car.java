
import javafx.util.Pair;

public class Car {
    private final String name;
    private final int topSpeed;

    public Car(String name, int topSpeed) {
        this.name = name;
        this.topSpeed = topSpeed;
    }

    // Later
    public Car(Pair<String, Integer> inputs) {
        this.name = inputs.getKey();
        this.topSpeed = inputs.getValue();
    }

    public String getName() {
        return name;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public boolean hasHigherTopSpeed(Car car) {
        return topSpeed < car.getTopSpeed();
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", topSpeed=" + topSpeed +
                '}';
    }
}
