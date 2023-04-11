package com.monterdev.monterdepos.util;

public class StringUtil {

    public static String cleanString(String dirtyString){
        return dirtyString.replaceAll("\\s+","");
    }

    public static String numbersOnly(String nonNumbers){
        return nonNumbers.replaceAll("[^\\d.]", "");
    }

}
