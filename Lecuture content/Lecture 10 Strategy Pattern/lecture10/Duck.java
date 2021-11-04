
//public abstract class Duck {
//
//    public void quack() {
//        System.out.println("quack");
//
//    public void swim() {
//        System.out.println("swimming");
//    }
//
//    public abstract void display();
//
//    // Comes later
//    public void fly() {
//        System.out.println("I'm flying!");
//    }
//}

// Take 2
public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    public void swim() {
        System.out.println("swimming");
    }

    public abstract void display();

    public void doQuack() {
        quackBehavior.quack();
    }

    public void doFly() {
        flyBehavior.fly();
    }

    // Comes later
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}

// Then add these to constructor of MallardDuck
