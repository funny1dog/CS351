
import java.util.concurrent.Semaphore;

public class LoginQueue {
    private final Semaphore semaphore;

    public LoginQueue(int length) {
        semaphore = new Semaphore(length);
    }

    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

    public int availableSlots() {
        return semaphore.availablePermits();
    }
}
