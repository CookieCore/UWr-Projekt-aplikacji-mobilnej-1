package com.example.iugales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iugales.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;

    private Button debugLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        debugLogoutBtn = view.findViewById(R.id.debug_logout);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currUsr = mAuth.getCurrentUser();
        if(currUsr != null) {
            String email = currUsr.getEmail();
            debugLogoutBtn.setText("Logged as " + email + ", Logout(DEBUG)");
        } else {
            debugLogoutBtn.setText("Logged as NULL, (no need to)Logout(DEBUG)");
        }

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

        binding.debugLogout.setOnClickListener(v -> {
            mAuth.signOut();
            debugLogoutBtn.setText("Logged as NULL, (no need to)Logout(DEBUG)");
        });
    }
}