
public class ReceivingThread implements Runnable {
    private final MySemaphore semaphore;

    public ReceivingThread(MySemaphore semaphore) {
        this.semaphore = semaphore;
    }

    /**
     * Wait for the sending thread to be done then before some "work"
     */
    @Override
    public void run() {
        semaphore.waitForRelease();

        System.out.println("Now I can run!");
    }
}
