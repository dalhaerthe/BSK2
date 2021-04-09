package controllers;

import utils.CryptUtil;
import utils.Dialogs;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


//wczytywanie plików - TODO: czy wczytywać cały plik do jednej zmiennej string ??

public class FilesController {


    public static final String INPUT_FILE = CryptUtil.FILES_PATH + "dane.txt";

    /**
     * odczytuje plik do szyfrowania strumieniowego
     * @param file
     * @return lista bajtów odczytanych z pliku
     */
    public static List<Byte> readFileToStreram(File file) {
        RandomAccessFile stream=null;

        try {
            stream = new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Byte> byteList = new LinkedList<>();


        while(true)

        try {
        byteList.add(stream.readByte());

        } catch (EOFException eof){
            break;
        } catch (IOException e) {
            e.printStackTrace();
        }

        /// kontrolnie
        for (byte b: byteList
             ) System.out.println(Integer.toBinaryString(b));

return byteList;

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
