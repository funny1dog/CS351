
/**
 * A binary semaphore that only allows you to communicate between threads or guard a critical region
 * or resource
 */
public class MySemaphore {
    private boolean signal = false;

    public synchronized void allowTake() {
        signal = true;
        System.out.println("Notifying.");
        notify();
    }

    /**
     * Why do we need a loop around wait?
     *
     * 1. Another thread could have obtained the lock and changed signal before we woke up
     * 2. Another thread could have invoked notify accidentally when the condition does not hold
     * 3. The notifying thread could have used notifyAll even if only some of the waiting threads
     *    are in the correct state
     * 4. Could spuriously wake up for no reason (rarely)
     */
    public synchronized void waitForRelease() {
        try {
            while (!signal) wait();
            System.out.println("Awake!");
            signal = false;
        }
        catch (InterruptedException exc) {
            System.out.println("exc = " + exc);
        }
    }
}
