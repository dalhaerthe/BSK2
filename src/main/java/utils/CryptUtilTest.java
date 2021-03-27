package utils;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.CryptUtil;

import java.io.FileNotFoundException;

import static org.testng.Assert.*;

public class CryptUtilTest {


    @Test
    public void testEncrypt1_shouldReturnTrue() {

        //given
        String key="2";
        boolean result = false;

        //when
        try {
            result =CryptUtil.encrypt1(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //then
        Assert.assertTrue(result);

    }

    @Test
    public void testDecrypt1() {

        //given
        String key="7";
        boolean result = false;

        //when
        try {
            result =CryptUtil.decrypt1(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //then
        Assert.assertTrue(result);


    }

@Test
    public void tst() throws FileNotFoundException {
   String a ="HERE IS A SECRET MESSAGE ENCIPHERED BY TRANSPOSITION";
   String key= "CONVENIENCE";

   System.out.println(CryptUtil.ps2_a1(a,key));
}


    @Test
    public void tstD() throws FileNotFoundException {
        String a ="HEEEDRINSITSIPAEIGHSERENEBNSAPTOTRCMERAOESCYS";
        String key= "CONVENIENCE";

        System.out.println(CryptUtil.ps2_a1_d(a,key));
    }

    @Test
    public void tstAlg3() throws FileNotFoundException {
        String a ="CRYPTOGRAPHY";
        String key= "BREAKBREAKBR";

        System.out.println(CryptUtil.ps2_a3(a,key));
    }

    @Test
    public void tstAlg3decr() throws FileNotFoundException {

        String key= "BREAKBREAKBR";

        System.out.println(CryptUtil.decrypt3(key));
    }


}