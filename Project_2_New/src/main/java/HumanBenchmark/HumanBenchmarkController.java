package HumanBenchmark;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HumanBenchmarkController extends AbstractController implements Initializable {
    @FXML
    private Label LabelAbout;
    @FXML
    private MenuItem MenuClose;
    @FXML
    private Button BtnReactionTime;
    private Scene scene;
    @FXML Button BtnClose;


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    // get to different panels
    public void initialize(URL location, ResourceBundle resources) {
        // following is popout related;
//        try {
//            HashMap<String, Object> resultMap = showPopupWindow();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
   }

    // menu control
    public void WTFCLOSE(ActionEvent actionEvent) {

    }

    // sample close button, works.
    public void BtnClose(ActionEvent actionEvent) {
        Stage stage = (Stage) BtnClose.getScene().getWindow();
        stage.close();
    }
    public void ActionMenuMainPage(ActionEvent actionEvent) {
        FXMLLoader loaderMainMenu = new FXMLLoader(getClass().getResource("HumanBenchmark.fxml"));
        try {
            Pane newRoot = loaderMainMenu.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ActionBtnReactionTime(ActionEvent actionEvent) throws IOException {
        // don't touch this part;
        FXMLLoader loaderActionReaction = new FXMLLoader(getClass().getResource("ReactionTime.fxml"));
        try {
            Pane newRoot = loaderActionReaction.load();
            ReactionTimeController controller = loaderActionReaction.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    //pop out windows related;
//    private HashMap<String, Object> showPopupWindow() throws IOException {
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("PopOutWindow.fxml"));
//        PopOutWindowController popupController = new PopOutWindowController();
//        loader.setController(popupController);
//        Parent layout;
//        try {
//            layout = loader.load();
//            Scene scene = new Scene(layout);
//            Stage popupStage = new Stage();
//            popupController.setStage(popupStage);
//            if(this.main!=null){
//                popupStage.initOwner(main.getPrimaryStage());
//            }
//            popupStage.initModality(Modality.WINDOW_MODAL);
//            popupStage.setScene(scene);
//            popupStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return popupController.getResult();
//    }

    public void ActionBtnAimTrainer(ActionEvent actionEvent) {
    FXMLLoader loaderActionBtnAimTrainer = new FXMLLoader(getClass().getResource("AimTrainer.fxml"));
        try {
        Pane newRoot2 = loaderActionBtnAimTrainer.load();
        AimTrainerController controller = loaderActionBtnAimTrainer.getController();
        controller.setScene(scene);
        scene.setRoot(newRoot2);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void ActionBtnSequenceMemory(ActionEvent actionEvent) {
        FXMLLoader loaderBtnSequenceMemory = new FXMLLoader(getClass().getResource("SequenceMemory.fxml"));
        try {
            Pane newRoot3 = loaderBtnSequenceMemory.load();
            SequenceMemoryController controller = loaderBtnSequenceMemory.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnChimpTest(ActionEvent actionEvent) {
        FXMLLoader loaderBtnChimpTest = new FXMLLoader(getClass().getResource("ChimpTest.fxml"));
        try {
            Pane newRoot4 = loaderBtnChimpTest.load();
            ChimpTestController controller = loaderBtnChimpTest.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnVisualMemory(ActionEvent actionEvent) {
        FXMLLoader loaderBtnVisualMemory = new FXMLLoader(getClass().getResource("VisualMemory.fxml"));
        try {
            Pane newRoot5 = loaderBtnVisualMemory.load();
            VisualMemoryController controller = loaderBtnVisualMemory.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionBtnNumberMemory(ActionEvent actionEvent) {
        FXMLLoader loaderBtnNumberMemory = new FXMLLoader(getClass().getResource("NumberMemory.fxml"));
        try {
            Pane newRoot6 = loaderBtnNumberMemory.load();
            NumberMemoryController controller = loaderBtnNumberMemory.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot6);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ActionBtnTypingTest(ActionEvent actionEvent) {
        FXMLLoader loaderBtnTypingTest = new FXMLLoader(getClass().getResource("TypingTest.fxml"));
        try {
            Pane newRoot7 = loaderBtnTypingTest.load();
            TypingTestController controller = loaderBtnTypingTest.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot7);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ActionBtnVerbalMemory(ActionEvent actionEvent) {
        FXMLLoader loaderBtnVerbalMemory = new FXMLLoader(getClass().getResource("VerbalMemory.fxml"));
        try {
            Pane newRoot8 = loaderBtnVerbalMemory.load();
            VerbalMemoryController controller = loaderBtnVerbalMemory.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


