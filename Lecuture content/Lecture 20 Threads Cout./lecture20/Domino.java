
public class Domino extends Thread {
    private static int count = 0;
    private Domino next;
    // Show results of making volatile
    private boolean standing = true;

    public Domino (Domino next) {
        setName("" + count++);
        this.next = next;
    }

    // After volatile show synchronizing getters
    public synchronized boolean isStanding() {
        return standing;
    }

    public synchronized void setStanding(boolean standing) {
        this.standing = standing;
    }

    public synchronized void topple() {
        setStanding(false);
        System.out.println(Thread.currentThread().getName() + " toppled " + getName());
    }

    @Override
    public void run() {
        // Problem is that one thread could check this then another then another
        // and so on. Thus 1 can topple 0 before 4 topples 3
        while (isStanding()) {
            // still standing
        }
        if (next != null) {
            next.topple();
        }
    }

    public static void main(String[] args) {
        Domino d5 = new Domino(null);
        Domino d4 = new Domino(d5);
        Domino d3 = new Domino(d4);
        Domino d2 = new Domino(d3);
        Domino d1 = new Domino(d2);

        d1.start();
        d2.start();
        d3.start();
        d4.start();
        d5.start();

        d1.topple();
    }
}
