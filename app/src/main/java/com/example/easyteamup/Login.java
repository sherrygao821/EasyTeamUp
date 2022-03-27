package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

        boolean isLoggedIn = ((MyApplication) this.getApplication()).isLoggedIn();

        // check log in status
        if(isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return;
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.loginButton);
        db = new DatabaseHelper(this);

        button.setOnClickListener(this::onClick);
    }

    /**
     * On click listener for the log in / sign up button
     * @param v
     * @author Sherry Gao
     */
    public void onClick(View v) {
        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();

        if(enteredEmail.equals("") || enteredPass.equals(""))
            Toast.makeText(Login.this, "Please Enter All Fields!", Toast.LENGTH_SHORT).show();
        else
            login(enteredEmail, enteredPass);
    }

    /**
     * Log in / Sign up function with database connection
     * @param enteredEmail
     * @param enteredPass
     * @author Sherry Gao
     */
    private void login(String enteredEmail, String enteredPass) {
        boolean checkUser = db.checkUser(enteredEmail);

        // log in
        if(checkUser) {
            Cursor cursor = db.checkUsernamePassword(enteredEmail, enteredPass);
            // log in succeeded
            if(cursor != null) {
                saveUserInfo(cursor, enteredEmail, enteredPass);
                Log.d("LOGIN", "Log in succeeded");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
            saveUserInfo(null, enteredEmail, enteredPass);
            Log.d("LOGIN", "Sign up succeeded");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Retrieve and save user info to global variable
     * @param cursor
     * @param enteredEmail
     * @param enteredPass
     */
    private void saveUserInfo(Cursor cursor, String enteredEmail, String enteredPass) {
        if(cursor == null) {
            User user = new User(enteredEmail, enteredPass);
            ((MyApplication) this.getApplication()).setUser(user);
        }
        else {
            User user = new User();
            user.setUserId(cursor.getInt(0));
            // TODO: uncomment after user table updated
//            user.setUserId(cursor.getString(1));
            ((MyApplication) this.getApplication()).setUser(user);
        }
    }
}