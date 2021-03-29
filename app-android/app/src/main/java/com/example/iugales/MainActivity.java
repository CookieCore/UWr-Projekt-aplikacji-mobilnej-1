package com.example.iugales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iugales.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.debugGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.debugGoHomePageUsr.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserHomePage.class);
            startActivity(intent);
        });

        binding.debugGoHomePageManager.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"Not Implement Yet", Toast.LENGTH_SHORT).show();
        });
    }
}