package com.kaushlendraprajapati.internshalaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaushlendraprajapati.internshalaapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        //check if user is logged in
        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor =sharedPreferences.edit();
        boolean isLoggedIn= sharedPreferences.getBoolean("boolean",false);

        if (!isLoggedIn){

            showLoginFragment();

        }
        else {
            showNotesFragment();
        }

    }

    private void showNotesFragment() {
       replaceFragment(new NotesFragment(),"Notes Fragment");
    }

    private void showLoginFragment() {
       replaceFragment(new LoginFragment(),"Login Fragment");
    }


    private void replaceFragment(Fragment fragment,String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment,tag);
        fragmentTransaction.commit();
        }

}