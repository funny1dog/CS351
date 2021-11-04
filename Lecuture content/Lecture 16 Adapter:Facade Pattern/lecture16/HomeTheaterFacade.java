import javafx.scene.layout.Pane;

public class HomeTheaterFacade {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private int e;
    private Pane p;

    public HomeTheaterFacade(int a, int b, int c, int d, int e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    @Override
    public String toString() {
        return "HomeTheaterFacade{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                '}';
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }
}
