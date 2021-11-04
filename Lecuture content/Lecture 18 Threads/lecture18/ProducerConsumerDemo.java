
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerDemo {
    public static void main(String[] args) {

        BlockingQueue<Integer> sharedQueue =
                new LinkedBlockingQueue<>();

        Thread prodThread =
                new Thread(new MyProducer("P", sharedQueue));
        Thread consThread =
                new Thread(new MyConsumer("C", sharedQueue));

        prodThread.start();
        consThread.start();
    }
}
