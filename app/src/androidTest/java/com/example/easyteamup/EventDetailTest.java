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
public class EventDetailTest {
    @Rule
    public ActivityTestRule<EventDetail> eventDetailActivityTestRule = new ActivityTestRule<>(EventDetail.class);

    private EventDetail eventDetail = null;

    @Before
    public void setUp() throws Exception {
        eventDetail = eventDetailActivityTestRule.getActivity();
    }

    @Test
    public void testEventWithdraw() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("isTest", true);
        eventDetail = eventDetailActivityTestRule.launchActivity(intent);

        DatabaseHelper db = new DatabaseHelper(eventDetail);
        List<Integer> participants = new ArrayList<>();
        participants.add(2);
        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");

        int id = db.addEvent(e, new ArrayList<Integer>(), false);
        boolean res = eventDetail.withdrawEventData(2, id);

        assertTrue(res);
    }

    @After
    public void tearDown() throws Exception {
        eventDetail = null;
    }
}
