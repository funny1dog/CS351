public class DominoBad extends Thread {
    private static int count = 0;

    private DominoBad next;
    private boolean standing = true;

    public DominoBad(DominoBad next) {
        setName("" + count++); // get/setName inherited from Thread
        this.next = next;
    }

    @Override
    public void run() {
        while(standing) {
            // remain standing
        }
        if (next != null) { next.topple(); }
    }

    public void topple() {
        standing = false;
        System.out.println(Thread.currentThread().getName()
                + " toppled " + getName());
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

        d1.topple(); // topple the first domino
    }
}
