
/**
 * BAD
 */
public class Atomicity {
    private int value = 0;

    public int getNext() {
        return value++;
    }
}
