package com.example.javafxstudying;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class KibicA extends Thread{
    Semaphore kontrola, kontrolaA1, kontrolaA2, kontrolaA3;
    Semaphore kontrolaB1, kontrolaB2, kontrolaB3;
    Stanowisko stanowisko1 = new Stanowisko(1, 3);
    Stanowisko stanowisko2 = new Stanowisko(2, 3);
    Stanowisko stanowisko3 = new Stanowisko(3, 3);
    public static int przepuszczeniaA = 0;
    Animacje config;
    int id;
    public KibicA(int id, Semaphore kontrola, Semaphore kontrolaA1, Semaphore kontrolaA2, Semaphore kontrolaA3, Semaphore kontrolaB1, Semaphore kontrolaB2, Semaphore kontrolaB3, Animacje config) {
        super("A - " + id);
        this.kontrola = kontrola;
        this.kontrolaA1 = kontrolaA1;
        this.kontrolaA2 = kontrolaA2;
        this.kontrolaA3 = kontrolaA3;
        this.kontrolaB1 = kontrolaB1;
        this.kontrolaB2 = kontrolaB2;
        this.kontrolaB3 = kontrolaB3;
        this.id = id;
        this.config = config;
    }
    public void inKibicA() throws InterruptedException {
        Random random = new Random();
        Circle circle = new Circle();
        System.out.println(Stanowisko.ilosc_kibicowA3 + " " + Stanowisko.ilosc_kibicowB3);
        Thread.sleep(random.nextInt(config.spowolnienieKolejki) + 10);

        //System.out.println("A przepuscil: " + przepuszczeniaA);
        if(Stanowisko.ilosc_kibicowB1 == 0) {
            try {
                kontrolaA1.acquire();
                Stanowisko.ilosc_kibicowA1++;
                System.out.println("Szybkosc: " + config.kibicA_animRate);
                Platform.runLater(() -> {
                    config.licznik1.setText("");
                    config.licznik1.setText(String.valueOf(Stanowisko.ilosc_kibicowA1));
                });

                System.out.println("Kibic " + getName() + " => " + stanowisko1.id + " Stan: " + Stanowisko.ilosc_kibicowA1);

                circle.setCenterX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                circle.setCenterY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                circle.setRadius(config.rozmiarKibicA/2);
                circle.setStroke(Color.BLACK);
                circle.setFill(config.colorA);

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().add(circle);
                });

                Path path = new Path();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                moveTo.setY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                LineTo lineTo = new LineTo();
                lineTo.setX(config.stanowisko1Cords.x + config.rozmiarStanowisko/2);
                lineTo.setY(config.stanowisko1Cords.y + config.rozmiarStanowisko/2);
                path.getElements().addAll(moveTo, lineTo);
                PathTransition pathTransition = new PathTransition(Duration.millis(340), path, circle);
                pathTransition.setRate(config.kibicA_animRate);
                config.kibicA_anim.add(pathTransition);

                pathTransition.setOnFinished(e -> {
                    unblock();
                    config.kibicA_anim.remove(pathTransition);
                });

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().addAll(path);
                    pathTransition.play();
                });
                block();
                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().remove(path);
                });

                Thread.sleep(random.nextInt(config.maxCzasKontroli) + config.minCzasKontroli);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            kontrolaA1.release();
            Stanowisko.ilosc_kibicowA1--;
            Platform.runLater(() -> {
                config.licznik1.setText("");
                config.licznik1.setText(String.valueOf(Stanowisko.ilosc_kibicowA1));
            });

            System.out.println("Kibic " + getName() + " <= " + stanowisko1.id + " Stan: " + Stanowisko.ilosc_kibicowB1);

            Path path1 = new Path();
            MoveTo moveTo1 = new MoveTo();
            moveTo1.setX(config.stanowisko1Cords.x + config.rozmiarStanowisko/2);
            moveTo1.setY(config.stanowisko1Cords.y + config.rozmiarStanowisko/2);

            LineTo lineTo1 = new LineTo();
            lineTo1.setX(config.stadionCords.x + config.rozmiarStadion/2);
            lineTo1.setY(config.stadionCords.y + config.rozmiarStadion/2);
            path1.getElements().addAll(moveTo1, lineTo1);

            PathTransition pathTransition1 = new PathTransition(Duration.millis(340), path1, circle);
            pathTransition1.setRate(config.kibicA_animRate);
            config.kibicA_anim.add(pathTransition1);

            pathTransition1.setOnFinished(e -> {
                unblock();
                config.kibicA_anim.remove(pathTransition1);
            });

            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().addAll(path1);
                pathTransition1.play();
            });
            block();
            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().remove(path1);
            });
        }
        else if(Stanowisko.ilosc_kibicowB2 == 0){
            try {
                kontrolaA2.acquire();
                Stanowisko.ilosc_kibicowA2++;
                Platform.runLater(() -> {
                    config.licznik2.setText("");
                    config.licznik2.setText(String.valueOf(Stanowisko.ilosc_kibicowA2));
                });

                System.out.println("Kibic " + getName() + " => " + stanowisko2.id + " Stan: " + Stanowisko.ilosc_kibicowA2);

                circle.setCenterX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                circle.setCenterY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                circle.setRadius(config.rozmiarKibicA/2);
                circle.setStroke(Color.BLACK);
                circle.setFill(config.colorA);

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().add(circle);
                });

                Path path = new Path();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                moveTo.setY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                LineTo lineTo = new LineTo();
                lineTo.setX(config.stanowisko2Cords.x + config.rozmiarStanowisko/2);
                lineTo.setY(config.stanowisko2Cords.y + config.rozmiarStanowisko/2);
                path.getElements().addAll(moveTo, lineTo);
                PathTransition pathTransition = new PathTransition(Duration.millis(340), path, circle);
                pathTransition.setRate(config.kibicA_animRate);
                config.kibicA_anim.add(pathTransition);

                pathTransition.setOnFinished(e -> {
                    unblock1();
                    config.kibicA_anim.remove(pathTransition);
                });

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().addAll(path);
                    pathTransition.play();
                });
                block1();
                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().remove(path);
                });

                Thread.sleep(random.nextInt(config.maxCzasKontroli) + config.minCzasKontroli);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            kontrolaA2.release();
            Stanowisko.ilosc_kibicowA2--;
            Platform.runLater(() -> {
                config.licznik2.setText("");
                config.licznik2.setText(String.valueOf(Stanowisko.ilosc_kibicowA2));
            });

            System.out.println("Kibic " + getName() + " <= " + stanowisko2.id + " Stan: " + Stanowisko.ilosc_kibicowA2);

            Path path1 = new Path();
            MoveTo moveTo1 = new MoveTo();
            moveTo1.setX(config.stanowisko2Cords.x + config.rozmiarStanowisko/2);
            moveTo1.setY(config.stanowisko2Cords.y + config.rozmiarStanowisko/2);

            LineTo lineTo1 = new LineTo();
            lineTo1.setX(config.stadionCords.x + config.rozmiarStadion/2);
            lineTo1.setY(config.stadionCords.y + config.rozmiarStadion/2);
            path1.getElements().addAll(moveTo1, lineTo1);

            PathTransition pathTransition1 = new PathTransition(Duration.millis(340), path1, circle);
            pathTransition1.setRate(config.kibicA_animRate);
            config.kibicA_anim.add(pathTransition1);

            pathTransition1.setOnFinished(e -> {
                unblock1();
                config.kibicA_anim.remove(pathTransition1);
            });

            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().addAll(path1);
                pathTransition1.play();
            });

            block1();

            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().remove(path1);
            });
        }
        else if (Stanowisko.ilosc_kibicowB3 == 0) {
            try {
                kontrolaA3.acquire();
                Stanowisko.ilosc_kibicowA3++;
                Platform.runLater(() -> {
                    config.licznik3.setText("");
                    config.licznik3.setText(String.valueOf(Stanowisko.ilosc_kibicowA3));
                });

                System.out.println("Kibic " + getName() + " => " + stanowisko3.id + " Stan: " + Stanowisko.ilosc_kibicowA3);

                circle.setCenterX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                circle.setCenterY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                circle.setRadius(config.rozmiarKibicA/2);
                circle.setStroke(Color.BLACK);
                circle.setFill(config.colorA);

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().add(circle);
                });

                Path path = new Path();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(config.kolejkaCords.x + config.rozmiarKolejka/2);
                moveTo.setY(config.kolejkaCords.y + config.rozmiarKolejka/2);
                LineTo lineTo = new LineTo();
                lineTo.setX(config.stanowisko3Cords.x + config.rozmiarStanowisko/2);
                lineTo.setY(config.stanowisko3Cords.y + config.rozmiarStanowisko/2);
                path.getElements().addAll(moveTo, lineTo);
                PathTransition pathTransition = new PathTransition(Duration.millis(340), path, circle);
                pathTransition.setRate(config.kibicA_animRate);
                config.kibicA_anim.add(pathTransition);

                pathTransition.setOnFinished(e -> {
                    unblock2();
                    config.kibicA_anim.remove(pathTransition);
                });

                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().addAll(path);
                    pathTransition.play();
                });
                block2();
                Platform.runLater(() ->{
                    HelloApplication.animationPane.getChildren().remove(path);
                });



                Thread.sleep(random.nextInt(config.maxCzasKontroli) + config.minCzasKontroli);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            kontrolaA3.release();
            Stanowisko.ilosc_kibicowA3--;
            Platform.runLater(() -> {
                config.licznik3.setText("");
                config.licznik3.setText(String.valueOf(Stanowisko.ilosc_kibicowA3));
            });

            System.out.println("Kibic " + getName() + " <= " + stanowisko3.id + " Stan: " + Stanowisko.ilosc_kibicowA3);

            Path path1 = new Path();
            MoveTo moveTo1 = new MoveTo();
            moveTo1.setX(config.stanowisko3Cords.x + config.rozmiarStanowisko/2);
            moveTo1.setY(config.stanowisko3Cords.y + config.rozmiarStanowisko/2);

            LineTo lineTo1 = new LineTo();
            lineTo1.setX(config.stadionCords.x + config.rozmiarStadion/2);
            lineTo1.setY(config.stadionCords.y + config.rozmiarStadion/2);
            path1.getElements().addAll(moveTo1, lineTo1);

            PathTransition pathTransition1 = new PathTransition(Duration.millis(340), path1, circle);
            pathTransition1.setRate(config.kibicA_animRate);
            config.kibicA_anim.add(pathTransition1);

            pathTransition1.setOnFinished(e -> {
                unblock2();
                config.kibicA_anim.remove(pathTransition1);
            });

            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().addAll(path1);
                pathTransition1.play();
            });

            block2();

            Platform.runLater(() ->{
                HelloApplication.animationPane.getChildren().remove(path1);
            });
        }
        else {
            przepuszczeniaA++;
            Thread.sleep(random.nextInt(10));
        }
    }

    public void run(){
        try {
            kontrola.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            inKibicA();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        kontrola.release();
    }
    Semaphore bin = new Semaphore(0);
    Semaphore bin1 = new Semaphore(0);
    Semaphore bin2 = new Semaphore(0);
    public void block() throws InterruptedException {
        bin.acquire();
    }

    public void unblock() {
        bin.release();
    }
    public void block1() throws InterruptedException {
        bin1.acquire();
    }

    public void unblock1() {
        bin1.release();
    }
    public void block2() throws InterruptedException {
        bin2.acquire();
    }

    public void unblock2() {
        bin2.release();
    }
}
