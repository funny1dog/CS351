
/*
    BAD
 */
public class PriorityThread implements Runnable {
    public int count;
    public final Thread thread;

    private static boolean stop = false;
    private static String currentName;

    public PriorityThread(String name) {
        thread = new Thread(this, name);
        count = 0;
        currentName = name;
    }

    @Override
    public void run() {
        System.out.println(thread.getName() + " starting.");

        do {
            count++;

            if (!currentName.equals(thread.getName())) {
                currentName = thread.getName();
                System.out.println("In " + currentName);
            }
        } while (!stop && count < 10_000_000);

        stop = true;

        System.out.println("\n" + thread.getName() + " terminating.");
    }
}
