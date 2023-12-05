package com.example.movieticketbooking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieticketbooking.Helper.FirebaseHelper;
import com.example.movieticketbooking.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, password1, password2, userName;
    private Button registerButton;
    private ImageView backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.editTextRegisterUsername);
        email = findViewById(R.id.editTextRegisterEmail);
        password1 = findViewById(R.id.editTextRegisterPassword);
        password2 = findViewById(R.id.editTextRegisterPasswordConfirm);
        backButton = findViewById(R.id.back_button4);
        registerButton = findViewById(R.id.registerButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password1.getText().toString().isEmpty() || password2.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String emailString = email.getText().toString();
                    String passwordString = password1.getText().toString();
                    String userNameString = userName.getText().toString();
                    FirebaseHelper.createAccount((Activity) RegisterActivity.this, mAuth, emailString, passwordString, userNameString);
                }
            }
        });
    }


}