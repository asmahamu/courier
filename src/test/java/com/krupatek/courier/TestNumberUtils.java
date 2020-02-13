package com.krupatek.courier;

import com.krupatek.courier.utils.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestNumberUtils {
    @Test
    public void testZeroSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "Zero";
        String actualOutput = numberUtils.convertNumberToWords(0.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testTwoDigitSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "Fifteen";
        String actualOutput = numberUtils.convertNumberToWords(15.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testThreeDigitNumberSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "One Hundred";
        String actualOutput = numberUtils.convertNumberToWords(100.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFourDigitNumberSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "Three Thousand Four Hundred and Thirty Five";
        String actualOutput = numberUtils.convertNumberToWords(3435.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFiveDigitNumberSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "Fifty Thousand Four Hundred and Thirty Five";
        String actualOutput = numberUtils.convertNumberToWords(50435.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testSixDigitNumberSuccess(){
        NumberUtils numberUtils = new NumberUtils();
        String expectedOutput = "Three Lac Forty Six Thousand Eight Hundred and Seventy Eight";
        String actualOutput = numberUtils.convertNumberToWords(346878.0f);
        Assert.assertEquals(expectedOutput, actualOutput);
    }}
