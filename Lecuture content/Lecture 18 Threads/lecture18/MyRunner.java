
public class MyRunner implements Runnable {
    private final Thread thread;
    private final Atomicity atomicity;

    private MyRunner(String name, Atomicity atomicity) {
        this.thread = new Thread(this, name);
        this.atomicity = atomicity;
    }

    public static MyRunner createAndRun(String name, Atomicity atomicity) {
        MyRunner myRunner = new MyRunner(name, atomicity);

        myRunner.thread.start();
        return myRunner;
    }

    @Override
    public void run() {
        int next = atomicity.getNext();

        while (next <= 10) {
            System.out.println(thread.getName() + ": next = " + next);
            next = atomicity.getNext();
        }
    }

    public static void main(String[] args) {
        Atomicity atomicity = new Atomicity();
        MyRunner.createAndRun("Runner1", atomicity);
        MyRunner.createAndRun("Runner2", atomicity);
    }
}
