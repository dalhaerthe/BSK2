package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.CryptoPrimitive;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.CryptUtil;
import utils.Dialogs;

import static java.lang.Thread.sleep;

public class PrimaryController {

    public static boolean notify_=false;

    @FXML
    private Button okButton1;    //algorytm 1
    @FXML
    private Button okButton2;    //algorytm 2
    @FXML
    private Button okButton3;    //algorytm 3
    @FXML
    private TextField keyField;

    @FXML
    private Label doneLabel;

    @FXML
    private ToggleButton modeButtonx;    //przycisk przełącznika trybu
    @FXML
    private boolean mode;       //false - szyfrowanie, true - deszyfrowanie
    private String key;

    @FXML
    ToggleButton x4;
    @FXML
    ToggleButton x3;
    @FXML
    ToggleButton x2;
    @FXML
    ToggleButton x1;
    @FXML
    ToggleButton x5;


    /**
     *
     * @return tablicę opisującą wielomian
     */

    public static Boolean[] getPolynomial() {
        return polynomial;
    }

    private static Boolean [] polynomial = new Boolean[5];   //wielomian w postaci mapy: potęga(index) -wartość (true/false)

    int register=0;



    @FXML
    void initialize() {

        //formatuje, by wejście nie akceptowało spacji
        spacesDeleter();
        //ustawia false - z jakiegoś powodu mimo ustawienia wartości i tak po pobraniu 'polynomial' dostajemy null
        for (Boolean b: polynomial
             ) {b=false;
        }
    }

    //kontroler przycisku zmiany tryby pracy
    @FXML
    public void changeMode(ActionEvent actionEvent) {


        if (modeButtonx.isSelected()) {
            mode = false;
            modeButtonx.setText("Tryb: szyfrowanie");
        } else {
            mode = true;
            modeButtonx.setText("Tryb: deszyfrowanie");
        }

    }

    @FXML
    public void readKey1(ActionEvent actionEvent) throws FileNotFoundException, InterruptedException {

        if (mode == false) {

            key = keyField.getText();

            if (validInputKey1()) return;
            showLabel("Szyfrowanie alg1. - gotowe!");

            CryptUtil.encrypt1(key);
        } else {
            key = keyField.getText();

            if (validInputKey1()) return;
            showLabel("Deszyfrowanie alg1. - gotowe!");

            CryptUtil.decrypt1(key);
        }

    }

    //wyświetla etykietę po operacji
    private void showLabel(String s) {
        doneLabel.setText(s);
        doneLabel.setVisible(true);
        try {
            Thread.sleep(500);
            //doneLabel.setText("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        doneLabel.setVisible(false);
    }

    /**
     * walidaja klucza do alg 1
     *
     * @return true = klucz niepoprawny
     */
    private boolean validInputKey1() {
        if (key.equals("") || key == null)
            return showBadKeyAllert();
        List<Character> keyList = new ArrayList<>(key.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));

        //sprawdza czy znaki zawierają się w a-z & A-Z
//ASCII: 65-90  97-122

        for (char x : keyList
        ) {
            if ((int) x < 65 || ((int) x > 90 && (int) x < 97) || (int) x > 122)
                return showBadKeyAllert();

//            else if(key.length()<7)
//            {
//                Dialogs.toShortKey3();
//                return true;
//            }
        }

        return false;


    }

    @FXML
    public void readKey2(ActionEvent actionEvent) throws FileNotFoundException {

        if (mode == false) {

            key = keyField.getText();

            if (validInputKey2()) return;

            showLabel("Szyfrowanie alg2. - gotowe!");

            CryptUtil.encrypt2(key);
        } else {
            key = keyField.getText();

            if (validInputKey2()) return;

            showLabel("Deszyfrowanie alg2. - gotowe!");


            CryptUtil.decrypt2(key);
        }


    }

    private boolean validInputKey2() {

        int x;
        try {

            x = Integer.valueOf(key);
        } catch (NumberFormatException e) {
            return showBadKeyAllert();
        }

        if (key.equals("") || key == null || x < 2 || x > 7) {
            return showBadKeyAllert();
        }
        return false;

    }

    private boolean showBadKeyAllert() {
        Dialogs.showBadKey();
        return true;
    }

    @FXML
    public void readKey3(ActionEvent actionEvent) throws FileNotFoundException {

        if (mode == false) {

            spacesDeleter();


            key = keyField.getText();

            if (validInputKey3()) return;


                showLabel("Szyfrowanie alg3. - gotowe!");



            CryptUtil.encrypt3(key);
        } else {
            key = keyField.getText();

            if (validInputKey3()) return;

            showLabel("Deszyfrowanie alg3. - gotowe!");


            CryptUtil.decrypt3(key);
        }
    }

    private void spacesDeleter() {
        keyField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }


    private boolean validInputKey3() {
        FilesController controller = new FilesController();
        try {
            controller.readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String inputText = controller.getText();
        inputText = inputText.replace(" ", "");      //pozbycie się spacji


        if (validInputKey1() == true)
            return true;   //czy hasło znakowe

        else if (key.length() < 3 || inputText.length() < 3)      //hasło musi byc >=3)
        {
            Dialogs.toShortKey2();
            return true;
        } else if (key.length() < inputText.length() - 1) {
            Dialogs.toShortKey();

            int tmp = (int) Math.ceil(inputText.length() / key.length());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < tmp; ++i)         //'przedłużanie' klucza przez zwlokrotnienie i przycięcie
                sb.append(key);

            key = sb.toString();



            if (key.length() > inputText.length()-1)
                keyField.setText(trimKey(key, inputText.length()));
            else keyField.setText(key);
        } else if (key.length() > inputText.length()) {
            Dialogs.toLongKey();

            key = (trimKey(key, inputText.length()));
            keyField.setText(key);

        }

        return false;       //false = ok
    }

    private String trimKey(String longKey, int lnght) {
        return longKey.substring(0, lnght - 1);
    }

    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);

    }

    //wczytuje help
    public void info(ActionEvent actionEvent) {
        try {
            App.setRoot("secondary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void about(ActionEvent actionEvent) {
        Dialogs.about();
    }

    /**
    *odczyt plików
     */
    public void viewFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add wave files");


        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null)
            FilesController.readFileToStreram(file);
    }

    /**
     * odczytuje układ przycisków do wprowadzania wielomianu
     * zapisuje od razu do liczby binarnej 5-bitowej
     * @param mouseEvent
     */

    public void readPolynomial(MouseEvent mouseEvent) {

if(x1.isSelected()==true)
    polynomial[0]=true;
else
    polynomial[0]=false;


    if(x2.isSelected()==true)
    polynomial[1]=true;
else
    polynomial[1]=false;

if(x3.isSelected()==true)
        polynomial[2]=true;
        else
        polynomial[2]=false;

        if(x4.isSelected()==true)
        polynomial[3]=true;
        else
        polynomial[3]=false;

        if(x5.isSelected()==true)
            polynomial[4]=true;
        else
            polynomial[4]=false;



        if(x1.isSelected()==true)
            register |=1;
        else
            register &= ~1;


        if(x2.isSelected()==true)
            register |=2;
        else
            register &= ~2;

        if(x3.isSelected()==true)
            register |=4;
        else
            register &= ~4;

        if(x4.isSelected()==true)
            register |=8;
        else
            register &= ~8;

        if(x5.isSelected()==true)
            register |=16;
        else
            register &= ~16;
        System.out.println(Integer.toBinaryString(register));       /// testowo

    }

    public void setNotify_(ActionEvent actionEvent) {
        notify_=true;
    }

    /**
     * uruchamia generowanie LFSR
     * @param actionEvent
     */
    public void startGenerator(ActionEvent actionEvent) {
        notify_=false;
        CryptUtil.LFSRGenerator();
    }
}

