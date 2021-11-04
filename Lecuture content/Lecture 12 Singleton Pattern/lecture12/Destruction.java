
import java.lang.reflect.Constructor;

public class Destruction {
    public static void main(String[] args) {
        EagerSingleton s1 = EagerSingleton.getInstance();
        EagerSingleton s2 = null;

        try {
            Constructor<?>[] constructors =
                    EagerSingleton.class.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);
                s2 = (EagerSingleton) constructor.newInstance();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("s1.hashCode() = " + s1.hashCode());
        System.out.println("s2.hashCode() = " + s2.hashCode());

        //

        EnumSingleton es1 = EnumSingleton.INSTANCE;
        EnumSingleton es2 = null;

        try {
            Constructor<?>[] constructors =
                    EnumSingleton.class.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);
                es2 = (EnumSingleton) constructor.newInstance();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("es1.hashCode() = " + es1.hashCode());
        System.out.println("es2.hashCode() = " + es2.hashCode());
    }
}
