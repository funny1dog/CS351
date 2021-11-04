
/**
 * This class corresponds to the pictures about the
 * Java memory model. This class represents one of
 * the threads.
 */
public class JMMRunnable implements Runnable {
    @Override
    public void run() {
        methodOne();
    }

    public void methodOne() {
        int localVariable1 = 42;

        SharedObject sharedObject = SharedObject.INSTANCE;

        methodTwo();
    }

    public void methodTwo() {
        // Object1 or Object4
        Integer localVariable1 = 869;
    }
}
