package com.example.leftlovers;

import androidx.appcompat.app.AppCompatActivity;
/*import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;*/

import android.os.Bundle;
// import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showPage(new SearchFragment());*/

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