package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.CryptoPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import utils.CryptUtil;
import utils.Dialogs;

import static java.lang.Thread.sleep;

public class PrimaryController {

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
    void initialize() {

        //formatuje, by wejście nie akceptowało spacji
spacesDeleter();    }

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
//TODO: zrefaktoryzować wczytywanie - ta sama metoda do każdego przycisku wystarczy

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

        if (key.equals("") || key == null || x < 2 || x>7) {
            return showBadKeyAllert();
        }
        return false;

//        int x;
//        try {
//
//            x = Integer.valueOf(key);
//        } catch (NumberFormatException e) {
//            return showBadKeyAllert();
//        }
//
//        if (key.equals("") || key == null || x < 1) {
//            return showBadKeyAllert();
//        }
//
//        int[] tab = CryptUtil.keyToArrayWrapper(key);
//
//
//        List<Integer> keyList = new ArrayList<>(tab.length);
//
//
//        for (int xx : tab            //konwersja tablicy na listę (Lists.newArrayList i  Arrays.asList tu nie działają) - wygodniejsza do operacji
//        ) {
//            keyList.add(xx);
//        }
//
//        Collections.sort(keyList);
//
//        if (keyList.get(0) != 1)           //czy zawiera 1
//        {
//            return showBadKeyAllert();
//        }
//
//        for (int i = 1; i < keyList.size(); i++)
//
//            if (!(i + 1 == keyList.get(i))) {
//
//                return showBadKeyAllert();
//            }
//
//        return false;
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

            {
                showLabel("Szyfrowanie alg3. - gotowe!");

            }


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
String result = controller.getText();
        result=result.replace(" ","");


return true; ///
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
}
