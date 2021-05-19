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

import com.example.iugales.databinding.ActivityUserHomePageBinding;
import com.example.iugales.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    public ActivityUserHomePageBinding mBinding;

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

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // navBar
        mBinding.navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                switch (item.getItemId()){
                    case R.id.navBar_userHome:
                        selected = new HomeFragment();
                        break;
                    case R.id.navBar_company:
                        selected = new CompanyFragment();
                        break;
                    case R.id.navBar_messages:
                        selected = new MessagesFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selected)
                        .commit();

                return true;
            }
        });
    }
}