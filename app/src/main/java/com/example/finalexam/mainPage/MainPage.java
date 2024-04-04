package com.example.finalexam.mainPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalexam.R;
import com.example.finalexam.databinding.ActivityMainPageBinding;
import com.example.finalexam.mainPage.fragments.AccountFragment;
import com.example.finalexam.mainPage.fragments.CVfragment;
import com.example.finalexam.mainPage.fragments.homepageFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainPage extends AppCompatActivity {

    ActivityMainPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new homepageFragment());

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_homepage)
                    replaceFragment(new homepageFragment());
                else if( item.getItemId()==R.id.menu_cv)
                    replaceFragment(new CVfragment());
                else if(item.getItemId()==R.id.menu_account)
                    replaceFragment(new AccountFragment());
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.displayPlace,fragment);
        fragmentTransaction.commit();
    }
    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.displayPlace,fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
}