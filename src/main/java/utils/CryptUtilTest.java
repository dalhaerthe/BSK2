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
    public void tst(){
    System.out.println("a");
    System.out.println(Character.toString((char) 98));
}

}