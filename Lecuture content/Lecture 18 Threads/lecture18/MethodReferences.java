
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodReferences {
    // Static methods
    public static void capitalize(String str) {
        String result = str.toUpperCase();
        System.out.println("result = " + result);
    }

    public static void staticMethods() {
        List<String> messages = Arrays.asList("hello", "world", "today");
        messages.forEach(MethodReferences::capitalize);
        System.out.println();
    }

    // Instance methods
    public static void instanceMethods() {
        Car myCar = new Car("Toyota", 100);
        List<Car> otherCars = Arrays.asList(
                new Car("Ford", 120),
                new Car("Honda", 90),
                new Car("Ferrari", 200)
        );

        Predicate<Car> pred = myCar::hasHigherTopSpeed;

        for (Car car : otherCars) {
            System.out.println("pred.test(car) = " + pred.test(car));
        }
        System.out.println();
    }

    // Arbitrary Instance Methods
    public static void instanceMethodArbitrary() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 18, 1, 4, 88);

        numbers.sort(Integer::compareTo);

        System.out.println("numbers = " + numbers);
        System.out.println();
    }

    // Constructor methods
    public static void constructors() {
        List<Pair<String, Integer>> cars = Arrays.asList(
                new Pair<>("Honda", 90),
                new Pair<>("Toyota", 100),
                new Pair<>("Ford", 120)
        );

        cars.stream()
                .map(Car::new)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        staticMethods();
        instanceMethods();
        instanceMethodArbitrary();
        constructors();
    }
}
