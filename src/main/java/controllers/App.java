package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.CryptUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {


        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws FileNotFoundException {

//        System.out.println("alg1\n" + CryptUtil.a1("CRYPTOGRAPHY",4));
//        System.out.println(CryptUtil.a1_deszyfr("CGRORYYTAHPP",4));
//        int[] key_a2 = {3, 1, 4, 2};
//        int[] key_a2_2 = {2, 5, 1, 6, 4, 3};
//        System.out.println("\nalg2\n" + CryptUtil.a2("CRYPTOGRAPHYOSA", key_a2));
//        System.out.println(CryptUtil.a2_deszyr("YCPRGTROHAYPAOS", key_a2));
//
//        System.out.println(CryptUtil.a2("BEZPIECZENSTWOSIECIKOM", key_a2_2));
//        System.out.println(CryptUtil.a2_deszyr("EIBEPZZSCTNEOEWCISKIMO", key_a2_2));
//
//        System.out.println("\nalg3\n" + CryptUtil.a3("HERE IS A SECRET MESSAGE ENCIPHERED by TRANSPOSITION","CONVENIENCE"));
//        System.out.println(CryptUtil.a3_deszyfr("HECRN CEYI ISEP SGDI RNTO AAES RMPN SSRO EEBT ETIA EEHS","CONVENIENCE"));


        launch();

    }

}