
public class SendingThread implements Runnable {
    private final MySemaphore semaphore;

    public SendingThread(MySemaphore semaphore) {
        this.semaphore = semaphore;
    }

    /**
     * Perform some "work" then allow the receiving thread to perform some "work"
     */
    @Override
    public void run() {
        for (int i = 0; i <= 10_000; i++) {
            System.out.println(i);
        }
        semaphore.allowTake();
    }
}
