package utils;

import javafx.scene.control.Alert;

public class Dialogs {

    public static void showOk() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Powodzenie");
        a.setContentText("Plik znajdziesz w katalogu: " + CryptUtil.FILES_PATH);
        a.setHeaderText("Szyfrowanie udane!");
        a.show();
    }

    public static void showBadKey() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Błędny klucz");
        a.setContentText("Więcej informacji w menu: Pomoc");
        a.setHeaderText("Proszę poprawnie wpisać klucz");
        a.show();
    }


    public static void showBadInputFile() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Problem z plikiem!");
        a.setContentText("Plik o nazwie 'dane.txt' musi znajdować się w: " + CryptUtil.FILES_PATH);
        a.setX(100);

        a.show();
    }


    public static void about() {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("O projekcie");
        a.setHeaderText("Projekt wykonali:");
        a.setContentText("Radosław Osikowicz i Rafał Żukowski");
        a.setX(100);

        a.show();


    }

    public static void noContent() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Problem z plikiem!");
        a.setHeaderText("Ostrzeżenie!");
        a.setContentText("Czy na pewno chcemy zaszyfrować pusty plik?");
        a.setX(100);

        a.show();
    }
}
