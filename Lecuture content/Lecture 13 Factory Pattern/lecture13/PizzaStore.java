
//public class PizzaStore {
//    public Pizza orderPizza(String type) {
//        Pizza pizza;
//
//        if (type.equalsIgnoreCase("cheese")) {
//            pizza = new CheesePizza();
//        }
//        else if (type.equalsIgnoreCase("greek")) {
//            pizza = new GreekPizza();
//        }
//        else {
//            pizza = new PepperoniPizza();
//        }
//
//        pizza.prepare();
//        pizza.bake();
//        pizza.cut();
//        pizza.box();
//
//        return pizza;
//    }
//}

//public class PizzaStore {
//    private SimplePizzaFactory factory;
//
//    public PizzaStore(SimplePizzaFactory factory) {
//        this.factory = factory;
//    }
//
//    public Pizza orderPizza(String type) {
//        Pizza pizza = factory.createPizza(type);
//
//        pizza.prepare();
//        pizza.bake();
//        pizza.cut();
//        pizza.box();
//
//        return pizza;
//    }
//}

public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    abstract Pizza createPizza(String type);
}