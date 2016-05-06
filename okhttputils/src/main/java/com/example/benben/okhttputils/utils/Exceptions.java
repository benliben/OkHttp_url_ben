package com.example.benben.okhttputils.utils;

/**
 * Created by benben on 2016/5/6.
 */
public class Exceptions {
    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }
}
