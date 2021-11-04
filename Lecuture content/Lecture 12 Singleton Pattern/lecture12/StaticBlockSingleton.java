
public class StaticBlockSingleton {
    private static final StaticBlockSingleton instance;

    private StaticBlockSingleton() {}

    static {
        try {
            System.out.println("Creating Static Block Singleton");
            instance = new StaticBlockSingleton();
        }
        catch (Exception e) {
            // Error handling here
            throw new RuntimeException("Oops");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {

    }

    /**
     * Pros:
     * Easy to understand
     * Cons:
     * Creates instance even if user doesn't need it
     */
}
