package com.example.project_2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerbalMemory extends Application {

    @FXML private Label lblLives;
    @FXML private Label lblScore;
    @FXML private TextArea textAreaWords;
    @FXML private Button btnSeen;
    @FXML private Button btnNew;
    private List<String> listOfWords = new ArrayList<String>();
    private List<String> seenWords = new ArrayList<String>();
    private int lives;
    private int scores;

    //    public static void main(String[] args) {
//        launch();
//    }
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Human.class.getResource("VerbalMemory.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 400, 480);
//        stage.setTitle("VerbalMemory");
//        stage.setScene(scene);
//        stage.show();
//        readFile();
//        toNewList();
//    }
    public void initialize() throws IOException {
        readFile();
    }

    // read file into an arraylist
    public void readFile() throws IOException {

        FileReader fr = new FileReader("dictionary.txt");
        BufferedReader bufferedReader = new BufferedReader(fr);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            listOfWords.add(line);
        }
        bufferedReader.close();
    }

    // send random item to TextArea, meanwhile add item into new Arraylist
    private void toNewList() {

        Random r=new Random();
        int randomNumber=r.nextInt(listOfWords.size());
        System.out.println(listOfWords.get(randomNumber));

    }

    //public void btnSeenAction()
   /*
    {
        btnSeen.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            toNewList();
            if (!seenWords.contains(textAreaWords.getText())){
                lives --;
                if (lives ==0) {
                    //GameOver()
                }
                else {
                    //PlayGame()
                }
            }
        });
    }

    */

    public void btnNewAction()
    {
        btnSeen.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if (seenWords.contains(textAreaWords.getText())){
                lives --;
                if (lives ==0) {
                    //GameOver()
                }
                else {
                    //PlayGame()
                }
            }
        });
    }

    public void btnSeenAction(ActionEvent actionEvent) {
        toNewList();
        if (!seenWords.contains(textAreaWords.getText())){
            lives --;
            if (lives ==0) {
                //GameOver()
            }
            else {
                //PlayGame()
            }
        }

    }
}

