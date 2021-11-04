
public class FibWorker extends Thread {
    private final String name;
    private long step = 0;
    private int x = 0;
    private int y = 1;
    private int z;
    private boolean keepGoing = true;

    public FibWorker(String name) {
        this.name = name;
        z = x + y;
    }

    private synchronized void update() {
        step++;
        if (z < 0) {
            // restart after overflow
            x = 0;
            y = 1;
        } else {
            x = y;
            y = z;
        }
        z = x + y;
    }

    public void quit() {
        keepGoing = false;
    }

    @Override
    public void run() {
        while(keepGoing) {
            update();
        }
        System.out.println(name +
                " stopping at step " + step);
    }

    @Override
    public synchronized String toString() {
        return name + " step " + step +
                ", x = " + x + ", y = " + y + ", z = " + z;
    }

    public static void main(String[] args) throws InterruptedException {

        FibWorker[] workers = new FibWorker[]{ new FibWorker("A"), new FibWorker("B") };

        for(FibWorker fibWorker : workers) { fibWorker.start(); }

        for(int i = 0; i < 10; ++i) {

            System.out.println("i = " + i);
            for(FibWorker fibWorker : workers) {
                System.out.println(fibWorker);
            }
            Thread.sleep(1000); // Take a short nap
        }

        for(FibWorker fibWorker : workers) { fibWorker.quit(); }

        for(FibWorker fibWorker : workers) {
            // wait until this thread has finished.
            fibWorker.join();
        }

        System.out.println("All workers are done. Goodbye.");
    }
}