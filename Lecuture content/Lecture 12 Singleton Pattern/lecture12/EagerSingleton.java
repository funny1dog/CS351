
public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * Pros:
     * Easy to understand
     * Can change your mind later if you don't want a Singleton anymore
     * Cons:
     * Instance created even if user doesn't need it
     * No exception handling
     */
}
