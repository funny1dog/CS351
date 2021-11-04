
/**
 * BAD
 */
public class PriorityDemo {
    public static void main(String[] args) {
        PriorityThread pt1 = new PriorityThread("High Priority");
        PriorityThread pt2 = new PriorityThread("Low Priority");
        PriorityThread pt3 = new PriorityThread("Normal Priority 1");
        PriorityThread pt4 = new PriorityThread("Normal Priority 2");
        PriorityThread pt5 = new PriorityThread("Normal Priority 3");

        pt1.thread.setPriority(Thread.NORM_PRIORITY + 2);
        pt2.thread.setPriority(Thread.NORM_PRIORITY - 2);

        pt1.thread.start();
        pt2.thread.start();
        pt3.thread.start();
        pt4.thread.start();
        pt5.thread.start();

        try {
            pt1.thread.join();
            pt2.thread.join();
            pt3.thread.join();
            pt4.thread.join();
            pt5.thread.join();
        }
        catch (InterruptedException exc) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("High priority thread counted to " + pt1.count);
        System.out.println("Low priority thread counted to " + pt2.count);
        System.out.println("Normal priority 1 thread counted to " + pt3.count);
        System.out.println("Normal priority 2 thread counted to " + pt4.count);
        System.out.println("Normal priority 3 thread counted to " + pt5.count);
    }
}
