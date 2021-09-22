package com.example.multiplecontrollertest;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

public class MainController {

    //一定要初始化这两个
    //并且，这个变量名一定是这样的模式：`<fx:id>Controller`
    //Eg，你的example.fxml中的`fx:id="example"`，那么你的变量名一定是`exampleController`
    //如果不信，欢迎尝试`NPE` :D
    //PS:fxml或者java文件名无所谓，只看`MainController`变量名，肉测!
    @FXML
    private Part1Controller part1Controller;

    @FXML
    private Part2Controller part2Controller;


    @FXML
    private void initialize() {
        part1Controller.injectMainController(this);
    }

    JFXTextArea getOutputPane() {
        return part2Controller.textArea;
    }
}