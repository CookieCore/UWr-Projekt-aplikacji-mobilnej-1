package com.example.iugales.util;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Util {

    public static Boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}
