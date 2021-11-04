
/**
 * BAD
 */
public class MyRunner implements Runnable {
    private final String name;
    private final Atomicity atomicity;

    public MyRunner(String name, Atomicity atomicity) {
        this.name = name;
        this.atomicity = atomicity;
    }

    @Override
    public void run() {
        int next = 0;

        while (next <= 10) {
            next = atomicity.getNext();
            System.out.println(name + ": " + next);
        }
    }

    public static void main(String[] args) {
        Atomicity atomicity = new Atomicity();
        Thread thread1 = new Thread(new MyRunner("thread 1", atomicity));
        Thread thread2 = new Thread(new MyRunner("thread 2", atomicity));

        thread1.start();
        thread2.start();
    }
}

//public class MyRunner implements Runnable {
//    private final Thread thread;
//    private final Atomicity atomicity;
//
//
//    private MyRunner(String name, Atomicity atomicity) {
//        this.atomicity = atomicity;
//        this.thread = new Thread(this, name);
//    }
//
//    public static MyRunner createAndRun(String name, Atomicity atomicity) {
//        MyRunner myRunner = new MyRunner(name, atomicity);
//
//        myRunner.thread.start();
//        return myRunner;
//    }
//
//    @Override
//    public void run() {
//        int next = 0;
//
//        while (next <= 10) {
//            next = atomicity.getNext();
//            System.out.println(thread.getName() + ": " + next);
//        }
//    }
//
//    public static void main(String[] args) {
//        Atomicity atomicity = new Atomicity();
//        MyRunner.createAndRun("Child 1", atomicity);
//        MyRunner.createAndRun("Child 2", atomicity);
//    }
//}
