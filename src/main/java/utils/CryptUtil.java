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
        transformInput();
        content = content.substring(1);   //wywalenie pierwszego niepotrzebnego znaku, który coś w którym momencie dodaje
        writeFile(ps2_a1(content, key), FILE_NAME1);
        return true;
    }

    public static String ps2_a1(String ciag, String klucz) {
        //string przychodzi juz bez spacji i jako wielkie litery
        //wyrzucanie spacji i zamienianie małych literek na wielkie


        String M = ciag;

//        for (int i = 0; i < ciag.length(); i++) {
//            if (ciag.charAt(i) >= 97) {
//                M += (char) (ciag.charAt(i) - 32);
//            } else if (ciag.charAt(i) == 32) {
//                continue;
//            } else {
//                M += ciag.charAt(i);
//            }
//        }

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

        //tablica pomocnicza do wpisywania literek
        String[] tp = new String[klucz.length()];
        int tpc = 0;
        for (int i = 0; i < klucz.length(); i++) {
            tp[i] = "";
        }


        int kisc = 0;
        int counter = 0;

        for (int i = 0; i < M.length(); i++) {
            if (counter <= kis[kisc]) {
                tp[tpc] += M.charAt(i);
                tpc++;
                counter++;

                // System.out.println(M.charAt(i) + "   tpc - " + tpc + "   counter - " + counter);
            } else {
                kisc++;
                counter = 0;
                tpc = 0;
                i--;

                //  System.out.println("else");
            }
        }

        String C = "";
        for (int i = 0; i < tp.length; i++) {
            C += tp[kis[i]];
            C += " ";
        }

        return C;
    }


    public static boolean decrypt1(String key) throws FileNotFoundException {
        readFile();
        content = content.substring(1);
        writeFile(ps2_a1_d(content, key), FILE_NAME1);
        return true;
    }

    public static String ps2_a1_d(String ciag, String klucz) {
        //zamienianie małych literek na wielkie
        String M = "";
        for (int i = 0; i < ciag.length(); i++) {
            if (ciag.charAt(i) >= 97) {
                M += (char) (ciag.charAt(i) - 32);
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


        //tablica pomocnicza do wpisywania literek
        String[] tp = new String[klucz.length()];
        int tpc = 0;
        for (int i = 0; i < klucz.length(); i++) {
            tp[i] = "";
        }


        //wypisywanie szyfru do tablicy stringow
        kisp = 0;
        //M length without spaces
        int Mlws = 0;
        for (int i = 0; i < ciag.length(); i++) {
            if (ciag.charAt(i) != 32) {
                tp[kis[kisp]] += ciag.charAt(i);
                Mlws++;
            } else {
                kisp++;
            }
        }

        //tablica pomocnicza int aby wiedziec czy juz wypisalismy literki z tablicy stringow
        int[] tpp = new int[tp.length];
        for (int i = 0; i < tpp.length; i++) {
            tpp[i] = 0;
        }


        //zamienianie posortowanego szyfru na ciag znakow M
        int kisc = 0;
        int counter = 0;

        String M2 = "";
        for (int i = 0; i < Mlws; i++) {
            if (counter < kis[kisc] + 1) {
                M2 += tp[counter].charAt(tpp[counter]);
                tpp[counter]++;
                counter++;
            } else {
                kisc++;
                i--;
                counter = 0;
            }
        }
        return M2;
    }


    /**
     * algorytm 2:
     *
     * @param key
     * @return  czy powodzenie
     * @throws FileNotFoundException
     */


    public static boolean encrypt2(String key) throws FileNotFoundException {

        readFile();
        int keyInt = Integer.parseInt(key);
        int tmp;
        StringBuilder sb = new StringBuilder();
        transformInput();

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

    /**
     * usuwa spacje z łańcucha wejściowego i zamienia małe litery na wielkie
     */

    private static void transformInput() {
        content=content.replace(" ","");
        content=content.toUpperCase(Locale.ROOT);
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
            tmp = (((tmp + (26 - keyInt)) % 26)) + 65;
            sb.append((char) tmp);
        }

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

        writeFile(sb.toString(), FILE_NAME3);
        return true;
    }

    private static List<Integer> lettersToNumbers(String key) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < key.length(); i++)

            integerList.add((int) key.charAt(i) - 97);
        return integerList;
    }


    /**
     * wrapper
     *
     * @param key
     * @return
     * @throws FileNotFoundException
     */
    public static boolean decrypt3(String key) throws FileNotFoundException {

        readFile();
        content = content.substring(1);           //usuwa zbędny znak na początku
        writeFile(ps2_a3_d(content, key), FILE_NAME3);
        return true;
    }

    public static String ps2_a3_d(String szyfr, String klucz) {
        String M = "";
        char k, c;
        for (int i = 0; i < szyfr.length(); i++) {
            k = (char) (klucz.charAt(i) - 65);
            c = (char) (szyfr.charAt(i) - 65);
            if (c > k) {
                M += (char) (((c - k) % 26) + 65);
            } else {
                M += (char) (((c + 26 - k) % 26) + 65);
            }

        }
        return M;
    }


}





