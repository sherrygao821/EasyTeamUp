package com.example.easyteamup;

import android.app.Application;

import com.example.easyteamup.classes.User;


/**
 * Global Variable storing current logged-in user information
 * @author Sherry Gao
 */
public class MyApplication extends Application {

    private User user;
    private boolean isLoggedIn;

    public MyApplication() {
        user = null;
        isLoggedIn = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
