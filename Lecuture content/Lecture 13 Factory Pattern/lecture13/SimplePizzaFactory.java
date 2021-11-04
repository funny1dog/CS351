
public class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza;

        if (type.equalsIgnoreCase("cheese")) {
            pizza = new CheesePizza();
        }
        else if (type.equalsIgnoreCase("pepperoni")) {
            pizza = new PepperoniPizza();
        }
        else if (type.equalsIgnoreCase("clam")) {
            pizza = new ClamPizza();
        }
        else {
            pizza = new VeggiePizza();
        }

        return pizza;
    }
}
