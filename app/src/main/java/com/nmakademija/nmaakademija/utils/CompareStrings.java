package com.nmakademija.nmaakademija.utils;

public class CompareStrings {
    public static boolean equal(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

}
