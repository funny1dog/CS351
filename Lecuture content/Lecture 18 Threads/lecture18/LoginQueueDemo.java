
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class LoginQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        int length = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(length);
        LoginQueue loginQueue = new LoginQueue(length);

        for (int i = 0; i < length; i++) {
            executorService.execute(() -> loginQueue.tryLogin());
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(0, loginQueue.availableSlots());

        loginQueue.logout();

        assertTrue(loginQueue.availableSlots() > 0);
        assertTrue(loginQueue.tryLogin());

        assertFalse(loginQueue.tryLogin());
    }
}
