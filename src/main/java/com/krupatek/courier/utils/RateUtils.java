package com.krupatek.courier.utils;

import org.springframework.stereotype.Component;

@Component
public class RateUtils {
    public Double charges(Double weight, Double from1, Double to1, Integer rate, Double addWeight, Double addRate){
        // Case 1 : Weight less than to1
        if(weight <= from1) return 0.0;
        else if(weight <= to1) return new Double(rate);
        else {
            weight = weight - to1;
            int extraWeightRate = 0;
            if(weight  % addWeight == 0){
                extraWeightRate = (int) (weight / addWeight);
            } else {
                extraWeightRate = (int) (1 + (weight / addWeight));
            }
            return new Double(rate) + (extraWeightRate * addRate);
        }
    }
}

