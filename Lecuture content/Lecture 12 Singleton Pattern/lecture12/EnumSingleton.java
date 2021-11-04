
public enum EnumSingleton {
    INSTANCE("Initial info");

    private String info;

    EnumSingleton(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Pros:
     * Thread safe
     * Not vulnerable to reflection
     * Cons:
     * No longer a class
     */

    public static void main(String[] args) {
        EnumSingleton es1 = EnumSingleton.INSTANCE;
        System.out.println("es1.getInfo() = " + es1.getInfo());

        EnumSingleton es2 = EnumSingleton.INSTANCE;
        es1.setInfo("Some other info");

        System.out.println("es1.getInfo() = " + es1.getInfo());
        System.out.println("es2.getInfo() = " + es2.getInfo());
    }
}
