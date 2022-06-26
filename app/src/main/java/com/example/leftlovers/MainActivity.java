package com.example.leftlovers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

        



/*        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_fridge) {
                Log.i("Nav-Check", "Fridge page");
                showPage(new FridgeFragment());
            } else if(item.getItemId() == R.id.page_bookmark) {
                Log.i("Nav-Check", "Bookmark page");
                showPage(new BookmarkFragment());
            } else {
                Log.i("Nav-Check", "Search page");
                showPage(new SearchFragment());
            }

            return true;
        });*/
    }

    /*private void showPage(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, fragment);
        fragmentTransaction.commit();
    }*/
}