package controllers;

import utils.CryptUtil;
import utils.Dialogs;

import java.io.*;
import java.util.Scanner;


//wczytywanie plików - TODO: czy wczytywać cały plik do jednej zmiennej string ??

public class FilesController {


    public static final String INPUT_FILE = CryptUtil.FILES_PATH + "dane.txt";

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
            result = result.replace("ą", "a").replace("ż", "z")
                    .replace("ź", "z").replace("ę", "e")
                    .replace("ł", "l").replace("ó", "o").replace("ć", "c");

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
