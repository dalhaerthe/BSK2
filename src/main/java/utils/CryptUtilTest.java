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
    public void tst_a1() throws FileNotFoundException {
   String a ="HEREISASECRETMESSAGEENCIPHEREDBYTRANSPOSITION";
   String key= "CONVENIENCE";

   System.out.println(CryptUtil.ps2_a1(a,key));
   //OK
}


    @Test
    public void tst_ps2_a1_d() throws FileNotFoundException {
        String a ="HEESPNIRRSSEESEIYASCBTEMGEPNANDICTRTAHSOIEERO";

        String key= "CONVENIENCE";

        System.out.println(CryptUtil.ps2_a1_d(a,key));

    }

    @Test
    public void tstAlg3()  {
        String a ="CRYPTOGRAPHY";
        String key= "BREAKBREAKBR";

        System.out.println(CryptUtil.ps2_a3(a,key));
    }

    @Test
    public void tstAlg3decr() throws FileNotFoundException {

        String key= "BREAKBREAKBR";
        String szyfr="DICPDPXVAZIP";

        System.out.println(CryptUtil.ps2_a3_d(szyfr,key));
    }


    @Test
    public void tstAlg3decrZInnymKluczem() throws FileNotFoundException {

        String key= "jeosuforywba";
        String szyfr="VGGJMDFCAKSJ";

        System.out.println(CryptUtil.ps2_a3_d(szyfr,key));
    }


    @Test
    public void tymczasowaDoEksperymentów(){

        int a=2;
        System.out.println(Integer.toBinaryString(a));
        a=a<<8;
        a+=2;
        System.out.println(Integer.toBinaryString(a));
a-=4;
        System.out.println(Integer.toBinaryString(a));



    }


}