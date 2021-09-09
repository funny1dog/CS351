import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Calculation {
    private int numOfPoints = 180;
    private double numOfFactor = 0.00;
    private final int radius = 200;
    private final int centerOfCircle = 250;
    private Group groupOfCircle = new Group();

    public Calculation(double numOfFactor, int numOfPoints, Color color) {
        this.numOfPoints = numOfPoints;
        this.numOfFactor = numOfFactor;

        Circle circle = new Circle(centerOfCircle,centerOfCircle, radius+1, Color.WHITE);
        groupOfCircle.getChildren().add(circle);

        for (int i = 0; i < numOfPoints; i++){
            double lengthOfArc = i * (2 * PI/numOfPoints) + PI;
            double pointPositionX = radius * cos (lengthOfArc);
            double pointPositionY = radius * sin (lengthOfArc);

            Circle pointsOfCircle = new Circle (pointPositionX + centerOfCircle,pointPositionY + centerOfCircle, 2, Color.BLACK);
            groupOfCircle.getChildren().add(pointsOfCircle);
        }

        for (int i = 0; i < numOfPoints; i++){
            double lengthOfArc = i * (2 * PI/numOfPoints) + PI;
            double nextLengthOfArc = (i * numOfFactor) * (2 * PI/numOfPoints) + PI;

            double startPointPositionX = (radius * Math.cos(lengthOfArc)) + centerOfCircle;
            double startPointPositionY = centerOfCircle + (radius * Math.sin(lengthOfArc));

            double endPointPositionX = (centerOfCircle + (radius * Math.cos(nextLengthOfArc)));
            double endPointPositionY = (centerOfCircle + (radius * Math.sin(nextLengthOfArc)));

            Line line = new Line(startPointPositionX, startPointPositionY, endPointPositionX, endPointPositionY);

            line.setStroke(color);
            groupOfCircle.getChildren().addAll(line);
        }
    }

    public Calculation() {

    }

    public void setNumOfFactor (double numOfFactor){
        this.numOfFactor = numOfFactor;
    }

    public void getNumOfFacotr (double numOfFactor){
        this.numOfFactor = numOfFactor;
    }

    public int getNumOfPoints() {
        return numOfPoints;
    }
    public void setNumOfPoints(int numOfPoints) {
        this.numOfPoints = numOfPoints;
    }
    public void show (Pane root){
        root.getChildren().addAll(groupOfCircle);
    }
}
