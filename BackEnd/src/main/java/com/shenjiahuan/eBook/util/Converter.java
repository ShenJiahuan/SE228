package com.shenjiahuan.eBook.util;

public class Converter {
    public static String[] asStrings(Object... objArray) {
        String[] strArray = new String[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            strArray[i] = String.valueOf(objArray[i]);
        }
        return strArray;
    }
}
