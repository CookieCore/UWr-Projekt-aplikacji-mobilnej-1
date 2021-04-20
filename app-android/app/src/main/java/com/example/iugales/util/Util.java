package com.example.iugales.util;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.iugales.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Util {


    public static Boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static Boolean isLoggedIn() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser curUsr = mAuth.getCurrentUser();
        if (curUsr != null) {
            if (!curUsr.isEmailVerified()) {
                // if logged in, but email not varied. Stay
                mAuth.signOut();
            } else {
                // if logged in, and email is varied. Go to main
                Intent intent = new Intent();
                return true;
            }
        }

        return false;
    }

}
