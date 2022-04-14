package com.example.easyteamup;

import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.easyteamup.classes.Event;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class EventSignUpTest {

    @Rule
    public ActivityTestRule<SignUpPop> signUpPopActivityTestRule = new ActivityTestRule<>(SignUpPop.class);

    private SignUpPop signUpPop = null;

    @Before
    public void setUp() throws Exception {
        signUpPop = signUpPopActivityTestRule.getActivity();
    }

    @Test
    public void signUpEventTest1() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("isTest", true);
        signUpPop = signUpPopActivityTestRule.launchActivity(intent);

        DatabaseHelper db = new DatabaseHelper(signUpPop);
        List<Integer> participants = new ArrayList<>();
        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");
        int id = db.addEvent(e, new ArrayList<Integer>(), false);

        boolean res = signUpPop.signUpEvent(id, 2, new HashMap<String, Integer>());

        assertTrue(res);
    }

    @Test
    public void signUpEventTest2() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("isTest", true);
        signUpPop = signUpPopActivityTestRule.launchActivity(intent);

        DatabaseHelper db = new DatabaseHelper(signUpPop);

        boolean res = signUpPop.signUpEvent(-1, 2, new HashMap<String, Integer>());

        assertFalse(res);
    }

    @After
    public void tearDown() throws Exception {
        signUpPop = null;
    }

}
