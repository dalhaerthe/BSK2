package controllers;


import utils.CryptUtil;
import utils.Dialogs;

import java.io.*;
import java.util.*;


//wczytywanie plików - TODO: czy wczytywać cały plik do jednej zmiennej string ??

public class FilesController {


    public static final String INPUT_FILE = CryptUtil.FILES_PATH + "dane.txt";

    /**
     * odczytuje plik do szyfrowania strumieniowego
     * @param file
     * @return tablica bajtów odczytanych z pliku
     */
    public static void readFileToStreram(File file) {

        InputStream inputStream=null;
        InputStream inputStream2=null;
        byte[] bytes=null;

        try {
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            bytes = new byte[inputStream.readAllBytes().length];
                    } catch (IOException e) {
            e.printStackTrace();
        }
// strumień po odczytaniu długości (potrzebnej do zainicjowania tablicy) nie pozwala na odczyt ponowny bajtów, stąd druga inicjalizacja
        try {
            inputStream=new FileInputStream(file);
            bytes=inputStream.readAllBytes();

        } catch (IOException e) {
            e.printStackTrace();
        }

      CryptUtil.encryptByStream(bytes);

    }


    public String getText() {
        return result;
    }

    private final StringBuilder textBuilder = new StringBuilder();
    private String result = "";
    private String line;

    public void readFile() throws FileNotFoundException {
        File plik = new File(INPUT_FILE);
        if (!(plik.exists()))
            Dialogs.showBadInputFile();
        else {



            Scanner in = new Scanner(plik, "UTF-8");



            while (in.hasNextLine())
                textBuilder.append(in.nextLine());

            result = textBuilder.toString();

            //zamiana poslkich znaków na odpowieniki bez 'ogonków'

            if(result.isEmpty() || result.equals("")|| result.equals(" "))
                Dialogs.noContent();
          result=CryptUtil.rmvPolishSigns(result);      //usunięcie polskich znaków

        }
    }


    public void writeFile(String content, String filename) throws FileNotFoundException {


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println(content);
        writer.close();

    }

}
