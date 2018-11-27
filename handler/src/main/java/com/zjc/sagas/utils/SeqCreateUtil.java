package com.zjc.sagas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * create by zjc on 18/11/26
 */
public class SeqCreateUtil {
    public static String create(String key){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = format.format(new Date());
        Random random = new Random();

        return key+s+getRandom3();
    }
    private static String getRandom3() {
        String ret = "";
        String randomNum = String.valueOf(new Random().nextInt(999));
        for (int i = randomNum.length(); i < 3; i++) {
            ret += "0";
        }
        ret += randomNum;
        return ret;
    }
}
