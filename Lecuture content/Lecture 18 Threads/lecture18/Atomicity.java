
import java.util.concurrent.atomic.AtomicInteger;

public class Atomicity {
    // BAD
//    private int counter = 0;
//
//    public int getNext() {
//        return counter++;
//    }

    // GOOD
//    private int counter = 0;
//
//    public synchronized int getNext() {
//        return counter++;
//    }

    // BETTER
    private AtomicInteger counter = new AtomicInteger();

    public int getNext() {
        return counter.getAndIncrement();
    }
}
