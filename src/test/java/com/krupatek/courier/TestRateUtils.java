package com.krupatek.courier;

import com.krupatek.courier.utils.RateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestRateUtils {
    @Test
    public void testChargesSuccessOne(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(5.0, 0.001, 0.5, 55, 0.5, 40.0);
        Assert.assertEquals(415.0, charges, 0.0);
    }

    @Test
    public void testChargesSuccessTwo(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(5.0, 0.001, 0.5, 55, 1.0, 40.0);
        Assert.assertEquals(255.0, charges, 0.0);
    }

    @Test
    public void testChargesSuccessThree(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(5.0, 0.001, 1.0, 55, 0.5, 40.0);
        Assert.assertEquals(375.0, charges, 0.0);
    }

    @Test
    public void testChargesSuccessFour(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(5.1, 0.001, 1.0, 55, 0.5, 40.0);
        Assert.assertEquals(415.0, charges, 0.0);
    }

    @Test
    public void testChargesSuccessFive(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(5.5, 0.001, 1.0, 55, 1.0, 40.0);
        Assert.assertEquals(255.0, charges, 0.0);
    }

    @Test
    public void testChargesSuccessSix(){
        RateUtils rateUtils = new RateUtils();
        Double charges = rateUtils.charges(0.0001, 0.001, 1.0, 55, 1.0, 40.0);
        Assert.assertEquals(0.0, charges, 0.0);
    }
}
