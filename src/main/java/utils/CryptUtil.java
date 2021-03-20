package utils;

import controllers.FilesController;

import java.io.FileNotFoundException;
import java.util.Locale;

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
     *alg 1 - wrapper
     * @param key
     * @return result
     * @throws FileNotFoundException
     */
    public static boolean encrypt1(String key) throws FileNotFoundException {
        readFile();
        writeFile(a1(content, Integer.parseInt(key)), FILE_NAME1);
        return true;
    }

    private static String a1(String ciag, int klucz) {
        /*
        tabs[1] C1              T1              A1
        tabs[2]     R2      P2      O2      R2      P2      Y2
        tabs[3]         Y3              G3              H3
         */


        String M = ciag;
        int n = klucz;

        // zmienna counter do ustalania położenia w wierszach
        int counter = 1;
        // zmienna asn do ustalania kierunku odliczania counter
        int asn = 0;
        // tablica stringów do wpisywania literek z naszego słowa
        String[] tabs = new String[n + 1];

        // wypełnienie tablicy pustym stringiem aby nie wypisaywało null
        for (int i = 1; i <= n; i++) {
            tabs[i] = "";
        }

        // pętla do wypełniania tablicy
        for (int i = 0; i < M.length(); i++) {
            // wpisywanie literek ze słowa do tablicy
            tabs[counter] += M.charAt(i);

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

        // zmienna zaszyfrowanego hasła
        String C = "";
        // łączenie tablicy
        for (int i = 1; i <= n; i++) {
            C += tabs[i];
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

        //97 - 122
        //65 - 90
        key=key.toLowerCase(Locale.ROOT);       //wszystko na wielkie litery
        ///



        readFile();
        int[] intTab = keyToArrayWrapper(key);
        writeFile(a2(content, intTab), FILE_NAME2);
        return true;
    }

    private static String a2(String ciag, int[] klucz) {
        String M = ciag;
        int[] key = klucz;
        String C = "";
        int counter = 1;
        int multiplier = 0;
        int position;

        for (int i = 0; i < M.length(); i++) {
            position = ((multiplier * key.length) - 1) + (key[counter - 1]);

            if ((position) < M.length()) {
                C += M.charAt(position);
            } else {
                i--;
            }

            if (counter == key.length) {
                counter = 0;
                multiplier++;
            }
            counter++;
        }

        return C;
    }

    /**
     * funkcja pomocnicza - string do int[]
     *
     * @param key
     * @return int Array
     */
    public static int[] keyToArrayWrapper(String key) {


        char[] temp = key.toCharArray();

        int[] intTab = new int[key.length()];

        for (int x = 0; x < key.length(); x++) {
            intTab[x] = (Integer.parseInt(String.valueOf(temp[x])));
        }
        return intTab;
    }


    public static boolean decrypt2(String key) throws FileNotFoundException {
        readFile();
        int[] intTab = keyToArrayWrapper(key);
        writeFile(a2_deszyr(content, intTab), FILE_NAME2);
        return true;
    }

    private static String a2_deszyr(String szyfr, int[] klucz) {

        String M = szyfr;
        String pomoc = "";

        int counter = 1;
        int multiplier = 0;
        int position;
        for (int i = 0; i < szyfr.length(); i++) {
            StringBuilder ciag = new StringBuilder(M);

            if ((klucz[counter - 1]) + (multiplier * klucz.length - 1) < szyfr.length()) {
                ciag.setCharAt((klucz[counter - 1]) + (multiplier * klucz.length - 1), szyfr.charAt(i));
            } else {
                i--;
                counter++;
                continue;
            }

            if (counter == klucz.length) {
                counter = 0;
                multiplier++;
            }
            counter++;
            M = ciag.toString();
        }
        return M;
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
        writeFile(a3(content, key), FILE_NAME3);
        return true;
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
}





