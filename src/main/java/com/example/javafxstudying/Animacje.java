package com.example.javafxstudying;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Animacje {
    public int ilosc_kibicow;
    public int rozmiarKibicA;
    public int rozmiarKibicB;
    public int rozmiarStanowisko;
    public int rozmiarKolejka;
    public int rozmiarStadion;
    int minCzasKontroli;
    int maxCzasKontroli;
    int spowolnienieKolejki;
    public XYCord stanowisko1Cords;
    public XYCord stanowisko2Cords;
    public XYCord stanowisko3Cords;
    public XYCord kolejkaCords;
    public XYCord stadionCords;
    public XYCord licznik1Cords;
    public XYCord licznik2Cords;
    public XYCord licznik3Cords;
    Rectangle stanowisko1;
    Rectangle stanowisko2;
    Rectangle stanowisko3;
    Rectangle kolejka;
    Rectangle stadion;
    Thread[] kibiceA;
    Thread[] kibiceB;
    List<Animation> kibicA_anim;
    List<Animation> kibicB_anim;
    double kibicA_animRate;
    double kibicB_animRate;
    Color colorA;
    Color colorB;
    Label licznik1;
    Label licznik2;
    Label licznik3;

    public Animacje(int ilosc_kibicow, int minCzasKontroli, int maxCzasKontroli, int spowolnienieKolejki, Color colorA, Color colorB){
        this.ilosc_kibicow = ilosc_kibicow;
        this.minCzasKontroli = minCzasKontroli;
        this.maxCzasKontroli = maxCzasKontroli;
        this.spowolnienieKolejki = spowolnienieKolejki;
        this.colorA = colorA;
        this.colorB = colorB;

        rozmiarKibicA = 50;
        rozmiarKibicB = 50;
        rozmiarStanowisko = 100;
        rozmiarKolejka = 90;
        rozmiarStadion = 90;

        stadionCords = new XYCord();
        stanowisko1Cords = new XYCord();
        stanowisko2Cords = new XYCord();
        stanowisko3Cords = new XYCord();
        kolejkaCords = new XYCord();
        licznik1Cords = new XYCord();
        licznik2Cords = new XYCord();
        licznik3Cords = new XYCord();

        stadionCords.x = 305;
        stadionCords.y = 40;

        stanowisko1Cords.x = 180;
        stanowisko1Cords.y = 250;

        stanowisko2Cords.x = 300;
        stanowisko2Cords.y = 250;

        stanowisko3Cords.x = 420;
        stanowisko3Cords.y = 250;

        kolejkaCords.x = 305;
        kolejkaCords.y = 460;

        licznik1Cords.x = stanowisko1Cords.x + rozmiarStanowisko;
        licznik1Cords.y = stanowisko1Cords.y - 15;

        licznik2Cords.x = stanowisko2Cords.x + rozmiarStanowisko;
        licznik2Cords.y = stanowisko2Cords.y - 15;

        licznik3Cords.x = stanowisko3Cords.x + rozmiarStanowisko;
        licznik3Cords.y = stanowisko3Cords.y - 15;
    }

    public void prepareAnimation(){
        kibicA_anim = new ArrayList<Animation>();
        kibicB_anim = new ArrayList<Animation>();
        kibicA_animRate = 1;
        kibicB_animRate = 1;

        stadion = new Rectangle(stadionCords.x, stadionCords.y, rozmiarStadion, rozmiarStadion);
        stadion.setStroke(Color.BLACK);
        stadion.setFill(Color.LIGHTGRAY);

        stanowisko1 = new Rectangle(stanowisko1Cords.x, stanowisko1Cords.y, rozmiarStanowisko, rozmiarStanowisko);
        stanowisko1.setStroke(Color.BLACK);
        stanowisko1.setFill(Color.LIGHTGRAY);


        stanowisko2 = new Rectangle(stanowisko2Cords.x, stanowisko2Cords.y, rozmiarStanowisko, rozmiarStanowisko);
        stanowisko2.setStroke(Color.BLACK);
        stanowisko2.setFill(Color.LIGHTGRAY);


        stanowisko3 = new Rectangle(stanowisko3Cords.x, stanowisko3Cords.y, rozmiarStanowisko, rozmiarStanowisko);
        stanowisko3.setStroke(Color.BLACK);
        stanowisko3.setFill(Color.LIGHTGRAY);


        kolejka = new Rectangle(kolejkaCords.x, kolejkaCords.y, rozmiarKolejka, rozmiarKolejka);
        kolejka.setStroke(Color.BLACK);
        kolejka.setFill(Color.LIGHTGRAY);

        licznik1 = new Label();
        licznik1.setLayoutX(licznik1Cords.x);
        licznik1.setLayoutY(licznik1Cords.y);

        licznik2 = new Label();
        licznik2.setLayoutX(licznik2Cords.x);
        licznik2.setLayoutY(licznik2Cords.y);

        licznik3 = new Label();
        licznik3.setLayoutX(licznik3Cords.x);
        licznik3.setLayoutY(licznik3Cords.y);

        HelloApplication.animationPane.getChildren().add(stanowisko1);
        HelloApplication.animationPane.getChildren().add(stanowisko2);
        HelloApplication.animationPane.getChildren().add(stanowisko3);
        HelloApplication.animationPane.getChildren().add(kolejka);
        HelloApplication.animationPane.getChildren().add(stadion);
        HelloApplication.animationPane.getChildren().addAll(licznik1, licznik2, licznik3);
    }
    public void startThreads(){
        int x = this.ilosc_kibicow; //liczba kibicow A
        KibicA[] kibiceA = new KibicA[x];
        KibicB[] kibiceB = new KibicB[x];
        Semaphore kontrola = new Semaphore(9);
        Semaphore kontrolaA1 = new Semaphore(3);
        Semaphore kontrolaA2 = new Semaphore(3);
        Semaphore kontrolaA3 = new Semaphore(3);
        Semaphore kontrolaB1 = new Semaphore(3);
        Semaphore kontrolaB2 = new Semaphore(3);
        Semaphore kontrolaB3 = new Semaphore(3);

        for (int i = 0; i < x; i++) {
            KibicA kibicA = new KibicA(i, kontrola, kontrolaA1, kontrolaA2, kontrolaA3, kontrolaB1, kontrolaB2, kontrolaB3, this);
            kibiceA[i] = kibicA;
        }

        for (int i = 0; i < x; i++) {
            KibicB kibicB = new KibicB(i, kontrola, kontrolaA1, kontrolaA2, kontrolaA3, kontrolaB1, kontrolaB2, kontrolaB3, this);
            kibiceB[i] = kibicB;
        }

        for (int i = 0; i < x; i++) {
            kibiceA[i].start();
            kibiceB[i].start();
        }
    }

    public void ustawSzybkoscA(double sliderSpeed){
        synchronized (kibicA_anim){
            if(sliderSpeed >= 0){
                kibicA_animRate = 0.04*sliderSpeed;
            }
            else
                kibicA_animRate = -0.004*sliderSpeed;
            for (Animation a : kibicA_anim)
                a.setRate(kibicA_animRate);
        }
    }
    public void zakonczAnimation(ActionEvent actionEvent) {
        Platform.exit();
    }
    public void ustawSzybkoscB(double sliderSpeed){
        synchronized (kibicB_anim){
            if(sliderSpeed >= 0){
                kibicB_animRate = 0.04*sliderSpeed;
            }
            else
                kibicB_animRate = -0.004*sliderSpeed;
            for (Animation a : kibicB_anim)
                a.setRate(kibicB_animRate);
        }
    }
    public void resumeAnimation(){
        synchronized (kibicA_anim){
            for (Animation a : kibicA_anim)
                a.play();
        }
        synchronized (kibicB_anim){
            for (Animation a : kibicB_anim)
                a.play();
        }
    }
    public void pauseAnimation(){
        synchronized (kibicA_anim){
            for (Animation a : kibicA_anim)
                a.pause();
        }
        synchronized (kibicB_anim){
            for (Animation a : kibicB_anim)
                a.pause();
        }
    }

//    public void interruptThreads(){
//        for (int i = 0; i < x; i++) {
//            kibiceA[i].join();
//            kibiceB[i].join();
//        }
//    }
}
