package HumanBenchmark;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PopOutWindowController extends AbstractController implements Initializable {
    @FXML private Label PopOutLabel;
    @FXML private Button BtnConfirm;
    private Stage stage = null;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stage stage = (Stage) BtnConfirm.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public HashMap<String, Object> getResult(){
        return this.result;
    }

    public void closeStage() {
        if(stage!=null){
            stage.close();
        }
    }
}
