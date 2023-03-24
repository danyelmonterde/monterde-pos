package com.monterdev.monterdepos.util;

import java.util.concurrent.ThreadLocalRandom;

public class TransactionNumberGenerator {

    private static int min = 1000;
    private static int max = 9998;

    public static String generateTransactionNumber(){
        String a = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
        String b = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
        String c = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
        String d = String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
        return a+b+c+d;
    }
}

