
/**
 * This class corresponds to the pictures about the
 * Java memory model. This class represents "Object 2"
 * which both threads have a reference to. This class
 * is a singleton.
 */
public class SharedObject {
    public static final SharedObject INSTANCE = new SharedObject();

    public Integer object3 = 21;

    public int member1 = 789;
    public String member2 = "hello";
}
