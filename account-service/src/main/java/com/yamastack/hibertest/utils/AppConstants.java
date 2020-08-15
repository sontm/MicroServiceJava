package com.yamastack.hibertest.utils;

import java.util.UUID;

public class AppConstants {
    public static String ROLE_NORMAL = "normal"; 
    public static String ROLE_ADMIN = "admin"; 

    public static String ACCOUNT_TYPE_LOCAL = "local"; 
    public static String ACCOUNT_TYPE_FB = "facebook"; 
    public static String ACCOUNT_TYPE_GOOGLE = "google"; 



    public static UUID generateRandomUUID() {
        return java.util.UUID.randomUUID();
    }
}