
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * Pros:
     * Does not create instance until user needs it
     * Cons:
     * Not thread safe
     */
}
