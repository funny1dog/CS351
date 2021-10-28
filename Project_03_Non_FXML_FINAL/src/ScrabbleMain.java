import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScrabbleMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final int WidthOfWindow = 1200;
        final int HeightOfWindow = 800;
        final int spacing = 10;
    }

    // read file
    private void ReadDictionary (SpellChecker spellChecker) throws IOException {
        BufferedReader br = new BufferedReader (new FileReader("Project_03_Non_FXML_FINAL/src/animals.txt"));
        String line = br.readLine();
        while (line != null) {
            spellChecker.insert(line);
            line = br.readLine();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
