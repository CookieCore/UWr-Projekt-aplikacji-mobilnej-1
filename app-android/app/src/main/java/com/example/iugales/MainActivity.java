package com.example.iugales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iugales.databinding.ActivityMainBinding;
import com.example.iugales.test.TestNavigationActivity;
import com.example.iugales.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Iugales); // turn off splash screen
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currUsr = mAuth.getCurrentUser();
        if(currUsr != null) {
            String email = currUsr.getEmail();
            binding.debugLogout.setText("Logged as " + email + ", Logout(DEBUG)");
        } else {
            binding.debugLogout.setText("Logged as NULL, (no need to)Logout(DEBUG)");
        }

        binding.debugGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.debugGoHomePageUsr.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        });

        binding.debugGoHomePageManager.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"Not Implement Yet", Toast.LENGTH_SHORT).show();
        });

        binding.debugLogout.setOnClickListener(v -> {
            mAuth.signOut();
            binding.debugLogout.setText("Logged as NULL, (no need to)Logout(DEBUG)");
        });

        binding.debugAuto.setOnClickListener(v -> {
            loadPage();
        });

        binding.testNav.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestNavigationActivity.class);
            startActivity(intent);
        });

        binding.debugLoginIndicator.setText("debug text");

    }

    public void loadPage() {
        Intent intent;
        if(Util.isLoggedIn()) {
            intent = new Intent(this, HomePageActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }
}