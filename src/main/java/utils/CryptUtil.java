package utils;

import controllers.FilesController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * logika szyfrująca
 */

public class CryptUtil {

    public static final String FILES_PATH = "c:/aaa/";
    public static final String FILE_NAME1 = FILES_PATH + "output_alg1.txt";       //ściezki do plików wyjściowych
    private static final String FILE_NAME2 = FILES_PATH + "output_alg2.txt";
    private static final String FILE_NAME3 = FILES_PATH + "output_alg3.txt";

    private static String content;
    private static FilesController filesController;

    public static String rmvPolishSigns(String text) {

        return text.replace("ą", "a").replace("ż", "z")
                .replace("ź", "z").replace("ę", "e")
                .replace("ł", "l").replace("ó", "o").replace("ć", "c");

    }


    private static void readFile() {
        filesController = getController();
        try {
            filesController.readFile();
        } catch (FileNotFoundException e) {
            Dialogs.showBadInputFile();
        }

        content = filesController.getText();

    }

    private static FilesController getController() {
        FilesController filesController = new FilesController();
        return filesController;
    }


    private static void writeFile(String a1, String fileName) throws FileNotFoundException {
        filesController.writeFile(a1, fileName);
    }


    //po jednej metodzie szyfrującej i deszyfrującej dla każdego algorytmu:

    /**
     * alg 1 - wrapper
     *
     * @param key
     * @return result
     * @throws FileNotFoundException
     */
    public static boolean encrypt1(String key) throws FileNotFoundException {
        readFile();
        content=content.substring(1);   //wywalenie pierwszego niepotrzebnego znaku, który coś w którym momencie dodaje
        writeFile(ps2_a1(content, key), FILE_NAME1);
        return true;
    }

    public static String ps2_a1(String ciag, String klucz)
    {
        //wyrzucanie spacji i zamienianie małych literek na wielkie
        String M = "";
        for (int i = 0; i < ciag.length(); i++)
        {
            if(ciag.charAt(i) >= 97)
            {
                M += (char)(ciag.charAt(i) - 32);
            }
            else if(ciag.charAt(i) == 32)
            {
                continue;
            }
            else
            {
                M += ciag.charAt(i);
            }
        }

        //sortowanie klucza i sortowanie pomocniczej tablicy z indeksami posortowanych literek klucza
        String keyso = klucz;
        //key index sorted
        int[] kis = new int[klucz.length()];
        int kisp;
        for ( int i = 0; i < klucz.length(); i++)
        {
            kis[i] = i;
        }

        for ( int i = 0; i < klucz.length()-1; i++)
        {
            for (int j = 0; j < klucz.length()-1; j++)
            {
                if(keyso.charAt(j) > keyso.charAt(j+1))
                {
                    kisp = kis[j];
                    kis[j] = kis[j+1];
                    kis[j+1] = kisp;

                    StringBuilder ciagsb = new StringBuilder(keyso);
                    ciagsb.setCharAt(j,keyso.charAt(j+1));
                    ciagsb.setCharAt(j+1,keyso.charAt(j));
                    keyso = ciagsb.toString();
                }
            }
        }

        for(int i = 0; i < kis.length; i++)
        {
            System.out.print(kis[i] + " ");
        }

        //tablica pomocnicza do wpisywania literek
        String[] tp = new String[klucz.length()];
        int tpc = 0;
        for (int i = 0; i < klucz.length(); i++)
        {
            tp[i] = "";
        }


        int kisc = 0;
        int counter = 0;

        for(int i = 0; i < M.length(); i++)
        {
            if(counter <= kis[kisc])
            {
                tp[tpc] += M.charAt(i);
                tpc++;
                counter++;

                System.out.println(M.charAt(i) + "   tpc - " + tpc + "   counter - " + counter);
            }
            else
            {
                kisc++;
                counter = 0;
                tpc = 0;
                i--;

                System.out.println("else");
            }
        }

        String C = "";
        for(int i = 0; i < tp.length; i++)
        {
            C+= tp[kis[i]];
            C+= " ";
        }

        return C;
    }


    public static boolean decrypt1(String key) throws FileNotFoundException {
        readFile();
        writeFile(a1_deszyfr(content, Integer.parseInt(key)), FILE_NAME1);
        return true;
    }

    private static String a1_deszyfr(String szyfr, int klucz) {
        /*
        działanie funkcji:

        1.
        xx
        xxxx
        xxxx
        xx

        2.
        CG
        RORY
        YTAH
        PP

        3.
        CRYPTOGRAPHY

         */
        String C = szyfr;
        int n = klucz;

        int counter = 1;
        int asn = 0;
        String[] tabs = new String[n + 1];

        // później przyda nam się ta tablica do ustalenia położenia wypisanych literek
        int[] tabs_length = new int[n + 1];

        // wypełnienie tablicy pustym stringiem aby nie wypisaywało null
        // wypelnianie tablicy zerami, bo później dodajemy do nich po 1
        for (int i = 1; i <= n; i++) {
            tabs[i] = "";
            tabs_length[i] = 0;
        }

        // pętla do wypełniania tablicy literkami
        // aby wiedziec jaka jest dlugosc poszczegolnych znakow w tablicy
        for (int i = 0; i < C.length(); i++) {
            tabs[counter] += "x";

            // w zależności czy nasz counter jest na samym górze lub dole zmieniamy asn
            if (counter == n) {
                asn = 1;
            } else if (counter == 1) {
                asn = 0;
            }

            // jeżeli asn jest na 0 to dodajemy counter - idziemy w doł po macierzy
            // jeżeli asn jest na 1 to odejmujemy counter - wracamy na górę macierzy
            if (asn == 0) {
                counter++;
            } else if (asn == 1) {
                counter--;
            }
        }

        // zmienna do określania do której komórki tablicy mają być wpisywane literki
        int tabs_counter = 1;

        //tabs_length_counter
        //zmienna do zliczania dlugosci znaku w tablicy
        int tlc = 1;
        String pomoc = "";

        // wpisaywanie odpowiednich literek z zaszyfrowanego klucza do tablic tak aby utworzyły macierz
        // gotową do rozkodowania
        for (int i = 0; i < C.length(); i++) {
            // dodawanie literki zaszyfrowanego hasla do zmiennej pomocniczej
            pomoc += C.charAt(i);

            // sprawdzanie czy długość zmiennej pomocniczej jest równe długości znaku w komórce tablicy
            if (tlc == tabs[tabs_counter].length()) {
                //zamiana ciągu znaku na znaki ze zmiennej pomocniczej
                tabs[tabs_counter] = pomoc;
                // przeniesienie się do kolejnej komórki tablicy
                tabs_counter++;
                // wyzerowanie zmiennych pomocniczych
                pomoc = "";
                tlc = 0;
            }
            tlc++;
        }

        String M = "";
        counter = 1;
        asn = 0;

        // konwertowanie tablicy znaków na odszyfrowane haslo
        for (int i = 0; i < C.length(); i++) {
            // dodawanie niewypisanego znaku z komórki tablicy
            M += tabs[counter].charAt(tabs_length[counter]);
            // oznaczenie wypisanego znaku
            tabs_length[counter]++;

            if (counter == n) {
                asn = 1;
            } else if (counter == 1) {
                asn = 0;
            }

            if (asn == 0) {
                counter++;
            } else if (asn == 1) {
                counter--;
            }
        }
        return M;
    }


    /**
     * algorytm 2:
     *
     * @param key
     * @return
     * @throws FileNotFoundException
     */


    public static boolean encrypt2(String key) throws FileNotFoundException {

        readFile();
        int keyInt = Integer.parseInt(key);
        int tmp;
        StringBuilder sb = new StringBuilder();
        List<Character> contentCharList = content.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        contentCharList.remove(0);      //korekta na tajemniczy znak istniejący na początku

        for (char letter : contentCharList
        ) {
            tmp = (int) letter - 65;
            tmp = ((tmp + keyInt) % 26) + 65;
            sb.append((char) tmp);
        }
        filesController.writeFile(sb.toString(), FILE_NAME2);
        return true;
    }


    public static boolean decrypt2(String key) throws FileNotFoundException {
        readFile();
        int keyInt = Integer.parseInt(key);
        int tmp;
        StringBuilder sb = new StringBuilder();
        List<Character> contentCharList = content.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        contentCharList.remove(0);      //korekta na tajemniczy znak istniejący na początku

        for (char letter : contentCharList
        ) {
            tmp = (int) letter - 65;
            tmp = (((tmp + (26-keyInt))%26))+65;
            sb.append((char) tmp);
        }
        System.out.println(sb.toString());  /// TEST
        filesController.writeFile(sb.toString(), FILE_NAME2);
        return true;
    }


    /**
     * algorytm 3.
     *
     * @param key
     * @return
     */

    public static boolean encrypt3(String key) throws FileNotFoundException {
        readFile();

        content = content.replaceAll(" ", "");     //usunięcie spacji
        key = key.toLowerCase(Locale.ROOT);               //zamiana liter na wielkie
        content = content.toLowerCase(Locale.ROOT);

        //algorytm3:

//key=rmvPolishSigns(key);
        List<Integer> keyEncoded, contentEncoded;
        keyEncoded = lettersToNumbers(key);           // tworzenie list znaków zakodowanych liczbą - kolejnością w alfabecie
        contentEncoded = lettersToNumbers(content);


        contentEncoded.remove(0);                   //usunięcie dziwnego znaku, który się dodaje prawdopodobnie z pliku i p[odstepnie generuje problemy ;)
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int x : contentEncoded
        ) {
            int tmp2 = (keyEncoded.get(i) + x) % 26;

            sb.append(Character.toString(tmp2 + 65));

            i++;

        }


        writeFile(sb.toString(), FILE_NAME3);/// so podmiany
        return true;
    }

    private static List<Integer> lettersToNumbers(String key) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < key.length(); i++)

            integerList.add((int) key.charAt(i) - 97);
        return integerList;
    }

    private static String a3(String ciag, String klucz) {
        //wyrzucanie spacji i zamienianie małych literek na wielkie
        String M = "";
        for (int i = 0; i < ciag.length(); i++) {
            if (ciag.charAt(i) >= 97) {
                M += (char) (ciag.charAt(i) - 32);
            } else if (ciag.charAt(i) == 32) {
                continue;
            } else {
                M += ciag.charAt(i);
            }
        }

        //sortowanie klucza i sortowanie pomocniczej tablicy z indeksami posortowanych literek klucza
        String keyso = klucz;
        //key index sorted
        int[] kis = new int[klucz.length()];
        int kisp;
        for (int i = 0; i < klucz.length(); i++) {
            kis[i] = i;
        }

        for (int i = 0; i < klucz.length() - 1; i++) {
            for (int j = 0; j < klucz.length() - 1; j++) {
                if (keyso.charAt(j) > keyso.charAt(j + 1)) {
                    kisp = kis[j];
                    kis[j] = kis[j + 1];
                    kis[j + 1] = kisp;

                    StringBuilder ciagsb = new StringBuilder(keyso);
                    ciagsb.setCharAt(j, keyso.charAt(j + 1));
                    ciagsb.setCharAt(j + 1, keyso.charAt(j));
                    keyso = ciagsb.toString();
                }
            }
        }


        // zamiana ciagu znaków na szyfr
        int i = 0;
        int multiplier = 0;
        int kisc = 0;
        int index;
        String C = "";
        while (i < M.length()) {
            index = (multiplier * klucz.length()) + kis[kisc];
            if (index < M.length()) {
                C += M.charAt((multiplier * klucz.length()) + kis[kisc]);
                i++;
                multiplier++;
            } else {
                C += " ";
                multiplier = 0;
                kisc++;
            }
        }

        return C;
    }


    public static boolean decrypt3(String key) throws FileNotFoundException {

        readFile();
        writeFile(a3_deszyfr(content, key), FILE_NAME3);
        return true;
    }


    private static String a3_deszyfr(String szyfr, String klucz) {
        //zamienianie małych literek na wielkie
        String C = "";
        for (int i = 0; i < szyfr.length(); i++) {
            if (szyfr.charAt(i) >= 97) {
                C += (char) (szyfr.charAt(i) - 32);
            } else {
                C += szyfr.charAt(i);
            }
        }

        //sortowanie klucza i sortowanie pomocniczej tablicy z indeksami posortowanych literek klucza
        String keyso = klucz;
        //key index sorted
        int[] kis = new int[klucz.length()];
        int kisp;
        for (int i = 0; i < klucz.length(); i++) {
            kis[i] = i;
        }

        for (int i = 0; i < klucz.length() - 1; i++) {
            for (int j = 0; j < klucz.length() - 1; j++) {
                if (keyso.charAt(j) > keyso.charAt(j + 1)) {
                    kisp = kis[j];
                    kis[j] = kis[j + 1];
                    kis[j + 1] = kisp;

                    StringBuilder ciagsb = new StringBuilder(keyso);
                    ciagsb.setCharAt(j, keyso.charAt(j + 1));
                    ciagsb.setCharAt(j + 1, keyso.charAt(j));
                    keyso = ciagsb.toString();
                }
            }
        }

        // wypełnianie ciągu znakami żeby było co zamieniać
        String M = "";
        for (int i = 0; i < C.length(); i++) {
            if (C.charAt(i) != 32) {
                M += "x";
            }
        }

        //wpisywanie znaków do ciągu z szyfru
        int i = 0;
        int multiplier = 0;
        int kisc = 0;
        int index;

        while (i < C.length()) {
            if (C.charAt(i) != 32) {
                index = (multiplier * klucz.length()) + kis[kisc];

                StringBuilder sb = new StringBuilder(M);
                sb.setCharAt(index, C.charAt(i));
                M = sb.toString();
                i++;
                multiplier++;
            } else {
                multiplier = 0;
                kisc++;
                i++;
            }
        }
        return M;
    }

    public static String ps2_a1(String ciag, String klucz)
    {
        //wyrzucanie spacji i zamienianie małych literek na wielkie
        String M = "";
        for (int i = 0; i < ciag.length(); i++)
        {
            if(ciag.charAt(i) >= 97)
            {
                M += (char)(ciag.charAt(i) - 32);
            }
            else if(ciag.charAt(i) == 32)
            {
                continue;
            }
            else
            {
                M += ciag.charAt(i);
            }
        }

        //sortowanie klucza i sortowanie pomocniczej tablicy z indeksami posortowanych literek klucza
        String keyso = klucz;
        //key index sorted
        int[] kis = new int[klucz.length()];
        int kisp;
        for ( int i = 0; i < klucz.length(); i++)
        {
            kis[i] = i;
        }

        for ( int i = 0; i < klucz.length()-1; i++)
        {
            for (int j = 0; j < klucz.length()-1; j++)
            {
                if(keyso.charAt(j) > keyso.charAt(j+1))
                {
                    kisp = kis[j];
                    kis[j] = kis[j+1];
                    kis[j+1] = kisp;

                    StringBuilder ciagsb = new StringBuilder(keyso);
                    ciagsb.setCharAt(j,keyso.charAt(j+1));
                    ciagsb.setCharAt(j+1,keyso.charAt(j));
                    keyso = ciagsb.toString();
                }
            }
        }

        for(int i = 0; i < kis.length; i++)
        {
            System.out.print(kis[i] + " ");
        }

        //tablica pomocnicza do wpisywania literek
        String[] tp = new String[klucz.length()];
        int tpc = 0;
        for (int i = 0; i < klucz.length(); i++)
        {
            tp[i] = "";
        }


        int kisc = 0;
        int counter = 0;

        for(int i = 0; i < M.length(); i++)
        {
            if(counter <= kis[kisc])
            {
                tp[tpc] += M.charAt(i);
                tpc++;
                counter++;

                System.out.println(M.charAt(i) + "   tpc - " + tpc + "   counter - " + counter);
            }
            else
            {
                kisc++;
                counter = 0;
                tpc = 0;
                i--;

                System.out.println("else");
            }
        }

        String C = "";
        for(int i = 0; i < tp.length; i++)
        {
            C+= tp[kis[i]];
            C+= " ";
        }

        return C;
    }
}





