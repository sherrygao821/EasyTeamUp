package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

        db = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.loginButton);

        button.setOnClickListener(this::onClick);
    }

    /**
     * Log in / sign up functionality
     * @param v
     * @author Sherry Gao
     */
    public void onClick(View v) {
        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();

        // check all fields are filled
        if(enteredEmail.equals("") || enteredPass.equals(""))
            Toast.makeText(Login.this, "Please Enter All Fields!", Toast.LENGTH_SHORT).show();
        else {
            int res = checkLogin(enteredEmail, enteredPass);

            // log in succeed
            if(res == 0) {
                saveUserInfo(enteredEmail, enteredPass);
                Log.d("LOGIN", "Log in succeeded");
                switchActivity();
            }
            // log in fail
            else if(res == 1) {
                Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN", "Log in failed");
            }
            // sign up succeed
            else if(res == 2){
                saveUserInfo(enteredEmail, enteredPass);
                Log.d("LOGIN", "Sign up succeeded");
                switchActivity();
            }
        }
    }

    /**
     * Proceed to main page
     * @author Sherry Gao
     */
    public void switchActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Log in / Sign up function with database connection
     * @param enteredEmail
     * @param enteredPass
     * @author Sherry Gao
     */
    public int checkLogin(String enteredEmail, String enteredPass) {

        boolean checkUser = db.checkUser(enteredEmail);

        // log in
        if(checkUser) {
            Cursor cursor = db.checkUsernamePassword(enteredEmail, enteredPass);
            if(cursor != null)
                return 0;
            else
                return 1;
        }
        // sign up
        else {
            User user = new User(enteredEmail, enteredPass);
            db.addUser(user);
            return 2;
        }
    }

    /**
     * Retrieve and save user info to global variable
     * @param enteredEmail
     * @param enteredPass
     * @author Sherry Gao
     */
    public void saveUserInfo(String enteredEmail, String enteredPass) {

        Cursor cursor = db.checkUsernamePassword(enteredEmail, enteredPass);

        // save user info as global variable
        ((MyApplication) this.getApplication()).setLoggedIn(true);
        if(cursor == null) {
            User user = new User(enteredEmail, enteredPass);
            ((MyApplication) this.getApplication()).setUser(user);
        }
        else {
            User user = new User();
            user.setUserId(cursor.getInt(0));
            user.setEmail(enteredEmail);
            user.setUserPwd(enteredPass);
            ((MyApplication) this.getApplication()).setUser(user);
        }
    }
}