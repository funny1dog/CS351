public class BoundedSemaphore {
    private int signals;
    private final int bound;

    public BoundedSemaphore(int bound) {
        signals = bound;
        this.bound = bound;
    }

    public synchronized void take() {
        try {
            while (signals == 0) wait();
            signals--;
            notify();
        }
        catch (InterruptedException exc) {
            System.out.println("exc = " + exc);
        }
    }

    public synchronized void release() {
        try {
            while (signals == bound) wait();
            signals++;
            notify();
        }
        catch (InterruptedException exc) {
            System.out.println("exc = " + exc);
        }
    }
}
