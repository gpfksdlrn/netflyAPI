package com.api.project.common.utils;

public class IdGenerator {
    public static long random_long(long min, long max) {
        double d = Math.random();
        long gap = max - min + 1;
        return (long) (d * gap) + min;
    }

    public static String random_id(long idx) {
        String l16 = Long.toHexString(idx);
        StringBuilder encode = new StringBuilder(Long.toHexString(l16.length()));

        for (int i = 0; i < l16.length(); ++i) encode.append(String.valueOf(l16.charAt(i)).toLowerCase());
        while (encode.length() < (24 - 3)) encode.append(Long.toHexString(random_long(0, 15)).toLowerCase());

        int i = 0;
        StringBuilder output = new StringBuilder();
        for (; i < 3; ++i) output.append("-").append(encode.toString(), i * 5, (i + 1) * 5);
        output.append("-").append(encode.substring(i * 5));

        return output.substring(1);
    }
}
