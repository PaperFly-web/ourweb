package com.paperfly.system.utils;

public class IsAssignSizeUtil {
    public static boolean size(String s,int size){
        if(s.length()>size){
            return false;
        }else {
            return true;
        }
    }
    public static boolean size(Integer s,int size){
        if(s>size){
            return false;
        }else {
            return true;
        }
    }
}
