import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// main class
public class SomePage {
    // setup basic variables. including a list, randome, scene and borderpane.
    private final List<SomePage> pages;
    private final ThreadLocalRandom rand;
    private final Scene scene;
    private final BorderPane root;

    // main method, need to pass in Scene and List<SomePage) into it.
    public SomePage(Scene scene, List<SomePage> pages) {
        this.pages = pages;
        this.rand = ThreadLocalRandom.current();
        this.scene = scene;

        // create a new BorderPane named root;
        root = new BorderPane();

        // create some basic things
        VBox controls = new VBox();
        Label curIndex = new Label(Integer.toString(pages.size()));
        TextField jumpToValue = new TextField("0");
        Button jumpToBtn = new Button("GO");


        jumpToBtn.setOnAction(event -> pages.get(Integer.parseInt(jumpToValue.getText())).show());
        Button addNewPage = new Button ("Add new Page");
        addNewPage .setOnAction(event -> {
            SomePage somePage = new SomePage(scene,pages);
            somePage.show();
        });
        controls.getChildren().addAll(curIndex, jumpToValue, jumpToBtn, addNewPage);
        root.setLeft(controls);

        Pane circles = new Pane();
        root.setCenter(circles);

        for (int i = 0; i<pages.size(); i++) {
            Circle circle = new Circle(
                    rand.nextDouble(scene.getWidth()),
                    rand.nextDouble(scene.getHeight()),
                    rand.nextDouble(10,50),
                    Color.color(
                            rand.nextDouble(), rand.nextDouble(), rand.nextDouble()
                    )
            );
            circles.getChildren().add(circle);
        }

        pages.add(this);
    }
    public void show() {scene.setRoot(root);}
}
