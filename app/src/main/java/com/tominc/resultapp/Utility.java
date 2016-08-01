package com.tominc.resultapp;

import java.util.regex.Pattern;

/**
 * Created by Ramola on 8/1/2016.
 */
public class Utility {

    private final static String REGEX="^(((IITU)1[4-5][1-2](0[1-9]|[1-2][0-9]))|(1((4MI5|(5MI[4-5])))(0[1-9]|[1-5][0-9]))|(1[2-5][1-5](((0[1-9]|[1-8][0-9]))|(90|91|92|93|94)))|(1[1-5]6(0[1-9]|[1-4][0-9]))|(1[3-5]7(0[1-9]|[1-5][0-9])))$";


    public static boolean checkRollno(String rollno){
        Pattern pattern=Pattern.compile(REGEX);
        if(pattern.matcher(rollno).matches()) return true;
        return false;
    }

}
