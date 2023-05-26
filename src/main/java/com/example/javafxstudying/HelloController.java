package com.example.javafxstudying;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.zip.DataFormatException;

public class HelloController {

    public Button startButton;
    public Button stopButton;
    public AnchorPane animationPane;
    public AnchorPane propertiesPane;
    public TextField ilosckibicowField;
    public Button zakonczButton;
    public TextField minTimeField;
    public TextField maxTimeField;
    public Button saveButton;
    public TextField slowTextField;
    public ColorPicker colorPickerA;
    public ColorPicker colorPickerB;
    public Slider speedSlider;
    public Label licznik2;
    public Label licznik1;
    public Label licznik3;
    List<Animation> animacje;

    public void startAnimation(ActionEvent actionEvent) {
        HelloApplication.config.startThreads();
        HelloApplication.animStatus = Animation.Status.RUNNING;
    }

    public void respasAnimation(ActionEvent actionEvent) {
        if(HelloApplication.config != null){
            if(HelloApplication.animStatus == Animation.Status.PAUSED){
                HelloApplication.animStatus = Animation.Status.RUNNING;
                HelloApplication.config.resumeAnimation();
                stopButton.setText("Pauza");
            } else if (HelloApplication.animStatus == Animation.Status.RUNNING) {
                HelloApplication.animStatus = Animation.Status.PAUSED;
                HelloApplication.config.pauseAnimation();
                stopButton.setText("Wznów");
            }
        }
    }
    public void zakonczAnimation(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void saveAnimation(ActionEvent actionEvent) {
        try{
            int ilosc_kibicow = 0;
            int minCzas = 0;
            int maxCzas = 0;
            int spowolnienieKolejki = 0;
            Color colorA, colorB;
            try{
                ilosc_kibicow = Integer.parseInt(ilosckibicowField.getText());
            } catch (NumberFormatException e){
                ilosckibicowField.setText("Error");
                throw new DataErrorException();
            }
            try {
                spowolnienieKolejki = Integer.parseInt(slowTextField.getText());
            } catch (NumberFormatException e) {
                throw new DataErrorException();
            }
            try{
                minCzas = Integer.parseInt(minTimeField.getText());
            } catch (NumberFormatException e){
                minTimeField.setText("Error");
                throw new DataErrorException();
            }
            try{
                maxCzas = Integer.parseInt(maxTimeField.getText());
            } catch (NumberFormatException e){
                maxTimeField.setText("Error");
                throw new DataErrorException();
            }
            colorA = colorPickerA.getValue();
            colorB = colorPickerB.getValue();

            HelloApplication.config = new Animacje(ilosc_kibicow, minCzas, maxCzas, spowolnienieKolejki, colorA, colorB);
            HelloApplication.config.prepareAnimation();


        } catch (DataErrorException e) {

        }

    }
    public void dostosujSzybkosc(){
        HelloApplication.config.ustawSzybkoscA(speedSlider.getValue());
        HelloApplication.config.ustawSzybkoscB(speedSlider.getValue());
    }
}