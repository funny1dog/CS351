
import static lecture14.NewYorkPizza.Size.*;
import static lecture14.Pizza.Topping.*;

public class Main {
    public static void main(String[] args) {
        // Telescoping Car
//        Car car1 = new Car(4);
//        System.out.println("car1.getDoors() = " + car1.getDoors());
//        System.out.println("car1.getWheels() = " + car1.getWheels());
//        System.out.println("car1.getSeats() = " + car1.getSeats());
        // Bean Car
//        Car car1 = new Car();
//        car1.setDoors(2);
//        car1.setSeats(4);
//        System.out.println("car1.getDoors() = " + car1.getDoors());
//        System.out.println("car1.getWheels() = " + car1.getWheels());
//        System.out.println("car1.getSeats() = " + car1.getSeats());
//        // Car Builder
//        CarBuilder carBuilder = new CarBuilder();
////        carBuilder.setDoors(2);
//        carBuilder.setWheels(5);
//        carBuilder.setSeats(4);
//
//        Car car2 = carBuilder.getCar();
//        System.out.println("car2.getDoors() = " + car2.getDoors());
//
//        // Car Builder fluent
//        carBuilder.doors(4).wheels(6).seats(8);
//
//        Car car3 = carBuilder.getCar();

        // New Car Builder
//        Car car = new Car.Builder(4, 4).wheels(5).build();
//        System.out.println("car.getWheels() = " + car.getWheels());
//
        // Pizza
        Pizza nypizza = new NewYorkPizza.Builder(MEDIUM)
                .addToppings(SAUSAGE)
                .addToppings(ONION)
                .build();

        System.out.println("nypizza = " + nypizza);
        
        // Car hierarchy
//        HondaCar car1 = new HondaCar.HondaBuilder(4, 4, "red").wheels(4).build();
//        System.out.println("car1.getDoors() = " + car1.getDoors());
//        System.out.println("car1.getWheels() = " + car1.getWheels());
//        System.out.println("car1.getSeats() = " + car1.getSeats());
//        System.out.println("car1.getColor() = " + car1.getColor());
    }
}
