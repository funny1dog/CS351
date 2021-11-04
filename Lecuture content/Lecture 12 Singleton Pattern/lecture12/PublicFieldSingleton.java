
public class PublicFieldSingleton {
    public static final PublicFieldSingleton INSTANCE = new PublicFieldSingleton();

    private PublicFieldSingleton() {}

    /**
     * Pros:
     * Very simple, clear it's a singleton
     * Cons:
     * Cannot change your mind if it is a singleton
     */
}
