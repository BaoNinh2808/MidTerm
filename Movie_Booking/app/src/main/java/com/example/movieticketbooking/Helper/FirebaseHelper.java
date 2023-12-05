package com.example.movieticketbooking.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movieticketbooking.Activity.LoginActivity;
import com.example.movieticketbooking.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseHelper {
    // Method to update UI based on user information
    public static void createAccount(Activity activity, FirebaseAuth mAuth, String email, String password, String userName) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            Toast.makeText(activity.getApplicationContext(), "Create account successful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                            updateUI(activity.getApplicationContext(), user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Create account failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }
    public static void updateUI(Context context, FirebaseUser user) {
        if (user != null) {
            // Update UI elements based on user information
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.putExtra("user", user);
            context.startActivity(mainIntent);
        } else {
            // Handle UI updates for signed-out or null user --> switch to login page
            Intent loginIntent = new Intent(context, LoginActivity.class);
            context.startActivity(loginIntent);
        }
    }

    public static void signIn(Activity activity, FirebaseAuth mAuth, String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(activity.getApplicationContext(), user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

}
