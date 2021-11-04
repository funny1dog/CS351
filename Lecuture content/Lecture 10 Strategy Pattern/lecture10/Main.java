
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();

        duck.doFly();
        duck.doQuack();

        duck.setFlyBehavior(new FlyNot());
        duck.setQuackBehavior(new Squeak());

        duck.doFly();
        duck.doQuack();

        // Lambda way
        FlyBehavior hover = () -> System.out.println("I'm hovering!");
        QuackBehavior peep = () -> System.out.println("peep");

        duck.setFlyBehavior(hover);
        duck.setQuackBehavior(peep);

        duck.doFly();
        duck.doQuack();
//
//        // Add one example
//        // Note types cannot be primitive
//        // because of generics
        Function<Integer, Integer> addOneLambda = n -> n + 1;

        BiFunction<Integer, Integer, Integer> addLambda =
                (x, y) -> x + y;

        // What if you wanted three, four, ...
        // Currying
        Function<Integer, Function<Integer, Function<Integer, Integer>>> addThreeLambda =
                x -> y -> z -> x + y + z;
    }

    public Integer addOne(Integer n) {
        return n + 1;
    }

    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    public Integer addThree(Integer x, Integer y, Integer z) {
        return x + y + z;
    }
}
