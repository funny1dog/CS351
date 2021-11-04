import java.util.concurrent.*;

public class TimeThreads {
    public static long timeThreads(Executor executor,
                                   int numThreads,
                                   Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(numThreads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done  = new CountDownLatch(numThreads);

        for (int i  = 0; i < numThreads; i++) {
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                    action.run();
                }
                catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws InterruptedException {
        Factorizer factorizer = new Factorizer();
        ExecutorService executor = Executors.newCachedThreadPool();

        long timeTaken = timeThreads(executor, 50, factorizer);

        System.out.println("timeTaken = " + TimeUnit.NANOSECONDS.toSeconds(timeTaken));

        executor.shutdown();
    }
}
