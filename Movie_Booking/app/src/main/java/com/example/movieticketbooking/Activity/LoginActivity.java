package com.example.movieticketbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieticketbooking.Helper.FirebaseHelper;
import com.example.movieticketbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText, passwordText;
    private TextView forgotPassText, registerText;
    private AppCompatButton loginButton;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.editTextEmail);
        passwordText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        forgotPassText = findViewById(R.id.forgotPassText);
        registerText = findViewById(R.id.registerText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill Your Username and Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    String email = emailText.getText().toString();
                    String password = passwordText.getText().toString();
                    //check if match with any username/password
                    FirebaseHelper.signIn((Activity) LoginActivity.this, mAuth, email, password);
                }
            }
        });

        forgotPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to forgot pass page
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}