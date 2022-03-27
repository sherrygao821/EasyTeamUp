package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyteamup.classes.User;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button button;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.loginButton);
        db = new DatabaseHelper(this);

        button.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();

        if(enteredEmail.equals("") || enteredPass.equals(""))
            Toast.makeText(Login.this, "Please Enter All Fields!", Toast.LENGTH_SHORT).show();
        else
            login(enteredEmail, enteredPass);
    }

    private void login(String enteredEmail, String enteredPass) {
        boolean checkUser = db.checkUser(enteredEmail);

        // log in
        if(checkUser) {
            boolean checkUsernamePassword = db.checkUsernamePassword(enteredEmail, enteredPass);
            // log in succeeded
            if(checkUsernamePassword) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Log.d("LOGIN", "Log in succeeded");
            }
            // log in failed
            else {
                Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN", "Log in failed");
            }
        }
        // sign up
        else {
            User user = new User(enteredEmail, enteredPass);
            db.addUser(user);

            Log.d("LOGIN", "Sign up succeeded");

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


}