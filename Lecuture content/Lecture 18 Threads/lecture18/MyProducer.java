
import java.util.concurrent.BlockingQueue;

public class MyProducer implements Runnable {
    private final String name;
    private final BlockingQueue<Integer> queue;

    public MyProducer(String name, BlockingQueue<Integer> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Start " + name);
        try {
            for(int i = 0; i < 20; i++) {
                System.out.println(name + " produces " + i);
                queue.put(i);
            }
            System.out.println(name + " is done producing");
        } catch (InterruptedException e) {
            System.out.println(name + " was interrupted");
        }
    }
}
