package com.kaushlendraprajapati.internshalaapp;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaushlendraprajapati.internshalaapp.databinding.FragmentLoginBinding;

import java.util.Objects;

import javax.security.auth.login.LoginException;


public class LoginFragment extends Fragment {

    FragmentLoginBinding loginBinding;
    private GoogleSignInClient googleSignInClient;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding=FragmentLoginBinding.inflate(inflater,container,false);
        return loginBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("924549272355-phhcq5jcrn627u8vui2rpr65jltfodrt.apps.googleusercontent.com")
                .build();

        googleSignInClient= GoogleSignIn.getClient(requireContext(),signInOptions);

        loginBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleSignInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(googleSignInIntent,10);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());

        if (account!=null){
            reload();
        }
    }

    private void reload() {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email = account.getEmail();
                String name = account.getDisplayName();
                Log.d("GoogleSignIn", "Email: " + email + ", Name: " + name);

                sharedPreferences= requireContext().getSharedPreferences("Login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name",email);
                editor.putBoolean("boolean",true);
                editor.apply();

                NotesFragment notesFragment = new NotesFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer,notesFragment ); // Replace the current fragment with NotesFragment
                transaction.addToBackStack(null);  // Optional: Add transaction to back stack
                transaction.commit();

            }
            catch (ApiException e){
                Log.e("GoogleSignIn", "Google SignIn Failed! Error Code: " + e.getStatusCode());
                Toast.makeText(requireContext(), "Google SignIn Failed!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}