
/**
 * Telescoping pattern
 * Allows you to have optional parameters and call different constructors
 * based on which parameters you want to set. Each constructor calls a larger
 * constructor with a default value until all values are specified at which point
 * the fields in the object are set and construction is complete.
 *
 * Pros:
 * Works but does not scale well. If you only have 1 or 2 optional parameters and less than 4 or 5
 * total parameters then this should be strongly considered.
 * Cons:
 * What if you only want set doors and seats explicitly? Currently, none of the constructors allow for
 * that. Thus, you would have to have a constructor for each combination of optional parameters or force
 * the user to enter default values themselves.
 */
//public class Car {
//    private final int doors;  // required
//    private final int wheels; // optional
//    private final int seats;  // optional
//
//    public Car(int doors) {
//        this(doors, 4);
//    }
//
//    public Car(int doors, int wheels) {
//        this(doors, wheels, 4);
//    }
//
//    public Car(int doors, int wheels, int seats) {
//        this.doors = doors;
//        this.wheels = wheels;
//        this.seats = seats;
//    }
//
//    public int getDoors() {
//        return doors;
//    }
//
//    public int getWheels() {
//        return wheels;
//    }
//
//    public int getSeats() {
//        return seats;
//    }
//}

import java.io.Serializable;

/**
 * Java Bean pattern
 * By making the Car class a bean, no arg constructor with getters and setters for each field,
 * we have effectively made every field optional with no boilerplate.
 * Pros:
 * Low boilerplate
 * Easy to understand
 * Cons:
 * Object can be left in an inconsistent state. For example what if we forget to set the number of seats?
 * No chance for immutability
 */
//public class Car implements Serializable {
//    private int doors;
//    private int wheels;
//    private int seats;
//
//    public Car () {}
//
//    public Car(int doors, int wheels, int seats) {
//        this.doors = doors;
//        this.wheels = wheels;
//        this.seats = seats;
//    }
//
//    public int getDoors() {
//        return doors;
//    }
//
//    public int getWheels() {
//        return wheels;
//    }
//
//    public int getSeats() {
//        return seats;
//    }
//
//    public void setDoors(int doors) {
//        this.doors = doors;
//    }
//
//    public void setWheels(int wheels) {
//        this.wheels = wheels;
//    }
//
//    public void setSeats(int seats) {
//        this.seats = seats;
//    }
//}

/**
 * Builder Pattern
 * Pattern outlined in the slides. Has a static inner class with a fluent (each set method returns this)
 * interface that builds up the Car. Then when the user is ready they can "build" the Car and receive
 * an instance of Car that is then immutable.
 * Pros:
 * Fluent API
 * Allows for immutability
 * Well suited to class hierarchies (see below)
 * Scales well
 * Cons:
 * Verbose
 * Must not only create an instance of the class itself but also the builder. This
 * is only really a concern in a very performance sensitive environment.
 */
//public class Car {
//    private final int doors;
//    private final int wheels;
//    private final int seats;
//
//    public static class Builder {
//        private final int doors; // required
//        private final int seats; // required
//        private int wheels = 4;  // optional
//
//        public Builder(int doors, int seats) {
//            // error handling here
//            this.doors = doors;
//            this.seats = seats;
//        }
//
//        public Builder wheels(int wheels) {
//            // error handling here
//            this.wheels = wheels;
//            return this;
//        }
//
//        public Car build() {
//            return new Car(this);
//        }
//    }
//
//    private Car (Builder builder) {
//        doors = builder.doors;
//        wheels = builder.wheels;
//        seats = builder.seats;
//    }
//
//    public int getDoors() {
//        return doors;
//    }
//
//    public int getWheels() {
//        return wheels;
//    }
//
//    public int getSeats() {
//        return seats;
//    }
//}

/**
 * Builder Pattern for Class Hierarchies
 * As stated previously the builder pattern can be extended to class hierarchies.
 * To achieve this goal we must make clever use of generics.
 * Pros and cons are the same as before.
 */
public abstract class Car {
    private final int doors;
    private final int wheels;
    private final int seats;

    public abstract static class Builder<T extends Builder<T>> {
        private final int doors; // required
        private final int seats; // required
        private int wheels = 4;  // optional

        protected Builder(int doors, int seats) {
            this.doors = doors;
            this.seats = seats;
        }

        public T wheels(int wheels) {
            this.wheels = wheels;
            return self();
        }

        public abstract Car build();
        // simulated self-type idiom
        protected abstract T self();
    }

    /**
     * Uses unbounded wildcard type "?". This can be interpreted as
     * accepting a Builder of some type. We don't care what the type is, it
     * has to type check of course but beyond that we don't care.
     */
    protected Car(Builder<?> builder) {
        this.doors = builder.doors;
        this.seats = builder.seats;
        this.wheels = builder.wheels;
    }

    public int getDoors() {
        return doors;
    }

    public int getWheels() {
        return wheels;
    }

    public int getSeats() {
        return seats;
    }
}