/**
 * Curious how we can prevent this at compile time??
 * Research linear types
 */
public class DoubleStart {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> System.out.println("Hello"));
        thread.start();
        thread.join();
        thread.start();
    }
}
