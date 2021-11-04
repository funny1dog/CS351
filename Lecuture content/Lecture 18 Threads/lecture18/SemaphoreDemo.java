
public class SemaphoreDemo {
    public static void main(String[] args) {
        MySemaphore semaphore = new MySemaphore();

        SendingThread st = new SendingThread(semaphore);
        ReceivingThread rt = new ReceivingThread(semaphore);

        Thread stt = new Thread(st);
        Thread rtt = new Thread(rt);

        stt.start();
        rtt.start();
    }
}
