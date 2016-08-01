package com.tominc.resultapp;

import java.util.regex.Pattern;

/**
 * Created by Ramola on 8/1/2016.
 */
public class Utility {

    private final String REGEX="^(((IITU)1[4-5][1-2][0-2][1-9])|(1[4-5]MI(([4-5])|(5))[0-5][1-9])|(1[2-5][1-5](([0-8][1-9])|(90|91|92|93|94)))|([11-15]6[0-4][1-9])|(1[3-5]7[0-5][1-9]))$";


    public static boolean checkRollno(String rollno){
        Pattern pattern=Pattern.compile(REGEX);
        if(pattern.matcher(rollno).matches()) return true;
        return false;
    }

}
