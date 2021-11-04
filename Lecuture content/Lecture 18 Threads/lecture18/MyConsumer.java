
import java.util.concurrent.BlockingQueue;

public class MyConsumer implements Runnable {
    private final String name;
    private final BlockingQueue<Integer> queue;

    public MyConsumer(String name, BlockingQueue<Integer> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Start " + name);
        try {
            while(true) {
                int value = queue.take();
                System.out.println(name + " consumes " + value);
            }
        } catch (InterruptedException e) {
            System.out.print(name + " was interrupted");
        }
    }
}
