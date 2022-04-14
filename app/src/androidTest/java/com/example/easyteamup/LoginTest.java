package com.example.easyteamup;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<Login> loginActivityTestRule = new ActivityTestRule<>(Login.class);

    private Login loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = loginActivityTestRule.getActivity();
    }

    @Test
    public void testSignUp() {
        int res = loginActivity.checkLogin("tester1@usc.edu", "12345");
        assertEquals("Test Sign Up", 2, res);
    }

    @Test
    public void testLoginSuccess() {
        loginActivity.checkLogin("tester2@usc.edu", "12345");
        int res = loginActivity.checkLogin("tester2@usc.edu", "12345");
        assertEquals("Test Log In Success", 0, res);
    }

    @Test
    public void testLoginFailed() {
        loginActivity.checkLogin("tester3@usc.edu", "12345");
        int res = loginActivity.checkLogin("tester3@usc.edu", "1");
        assertEquals("Test Log In Failure", 1, res);
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}