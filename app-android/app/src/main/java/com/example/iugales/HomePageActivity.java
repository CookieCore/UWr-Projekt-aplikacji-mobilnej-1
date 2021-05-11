package com.example.iugales;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.iugales.databinding.ActivityUserHomePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity {

    public ActivityUserHomePageBinding mBinding;
/*TODO fix bug with TakeMeHome always view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUserHomePageBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // select home from NavBar
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        // Check if user is signed in (non-null) and go back or stay.
        if (!Util.isLoggedIn()) {
            // if logged in, and email is varied. Go to main
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // navBar

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}