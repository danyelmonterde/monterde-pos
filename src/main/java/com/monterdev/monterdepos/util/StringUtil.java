package com.monterdev.monterdepos.util;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil {

    public static String cleanString(String dirtyString){
        return dirtyString.replaceAll("\\s+","");
    }

    public static String numbersOnly(String nonNumbers){
        return nonNumbers.replaceAll("[a-z]+","");
    }

}
