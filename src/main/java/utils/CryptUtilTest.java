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
    public void testKeyToArrayWrapper() {

//given

          String key="53241";
          int[] tab = {5,3,2,4,1};
          int[] result = new int[5];


          //when
     result = CryptUtil.keyToArrayWrapper(key);

//then
          Assert.assertEquals(result,tab);


    }

}