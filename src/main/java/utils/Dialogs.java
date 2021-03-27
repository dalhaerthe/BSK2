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

    public static void toShortKey() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Ostrzeżenie");
        a.setHeaderText("Hasło za krótkie.");
        a.setContentText("Podane hasło zostanie wydłużone przez zwielokrotnienie");
        a.setX(100);

        a.show();
    }

    public static void toLongKey() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Ostrzeżenie");
        a.setHeaderText("Hasło za długie.");
        a.setContentText("Podane hasło zostanie przycięte do wymaganego rozmiaru");
        a.setX(100);

        a.show();
    }

    public static void toShortKey2() {

                    Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Ostrzeżenie");
            a.setHeaderText("Hasło lub treść tekstu wejścia - za krótkie.");
            a.setContentText("Żeby szyforwanie miało sens, potrzebne sa min 3 znaki w hasle i pliku wejściowym");
            a.setX(100);

            a.show();


        }

    public static void toShortKey3() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Ostrzeżenie");
        a.setHeaderText("Hasło jest zbyt krótkie.");
        a.setContentText("Prosze podać klucz z min 8 znaków");
        a.setX(100);

        a.show();
    }
}
