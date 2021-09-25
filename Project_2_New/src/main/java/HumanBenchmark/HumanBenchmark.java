/*
# Total Points: 125

## Standards (40 pts)

- Follows coding standards (15 pts)
- Follows submission guidelines (10 pts)
- Has design documents for the overall structure (5 pts)
- Has design documents for each mini game (10 pts)

## Mini Games (70 pts)

- All games must follow the rules shown on the website
- All games must have a back button to return to the home screen (5 pts)
- All games must have a retry button to reset the game (5 pts)
- Reaction Timer (5 pts)
  - Structure given in class
	- If user clicks to early the game should be reset
- Sequence Memory (5 pts)
- Aim Trainer (5 pts)
- Chimp Test (10 pts)
  - Must go up to 18 numbers
- Visual Memory (10 pts)
  - Must go up to 20 white squares
- Typing (10 pts)
  - Must highlight any mistakes
- Number Memory (5 pts)
  - Must go up to 18 digit numbers
- Verbal Memory (5 pts)
  - Must use a bank of at least 100 words
- Custom Game (5 pts)

## Output File (15 pts)

- Columns are spelled correctly (2.5 pts)
- Columns are in the correct order (2.5 pts)
- Button on home page to save scores (5 pts)
- Columns use correct units (5 pts)

## Extra Credit (Up To 20 pts)

- Make UI user friendly and as close to the website version as possible (Up To 20 pts)
 */
package HumanBenchmark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HumanBenchmark extends Application {
    private Stage primaryStage;
    @Override
    public void start(Stage Primarystage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(HumanBenchmark.class.getResource("HumanBenchmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        HumanBenchmarkController controller = fxmlLoader.getController();
        controller.setScene(scene);

        Primarystage.setTitle("Human Benchmark");
        Primarystage.setScene(scene);
        Primarystage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

}