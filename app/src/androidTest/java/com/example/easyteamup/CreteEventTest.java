package com.example.easyteamup;

import static org.junit.Assert.*;

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
public class CreteEventTest {

    @Rule
    public ActivityTestRule<CreateEvent> createEventActivityTestRule = new ActivityTestRule<>(CreateEvent.class);

    private CreateEvent createEvent = null;

    @Before
    public void setUp() throws Exception {
        createEvent = createEventActivityTestRule.getActivity();
    }

    @Test
    public void AddEventTest() {

        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");

        List<Integer> inviteeList = new ArrayList<>();

        boolean isEdit = false;

        boolean res = createEvent.changeEventData(e, inviteeList, isEdit);

        assertTrue(res);
    }

    @Test
    public void AddEventTestWithInvitees() {

        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");

        List<Integer> inviteeList = new ArrayList<>();
        inviteeList.add(1);
        inviteeList.add(2);

        boolean isEdit = false;

        boolean res = createEvent.changeEventData(e, inviteeList, isEdit);

        assertTrue(res);
    }

    @Test
    public void editEvent() {

        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");
        List<Integer> participants = new ArrayList<>();
        e.setEvtParticipants(participants);
        List<Integer> inviteeList = new ArrayList<>();

        boolean isEdit = false;

        createEvent.changeEventData(e, inviteeList, isEdit);

        isEdit = true;

        e.setEvtName("Test Edit Event");

        boolean res = createEvent.changeEventData(e, inviteeList, isEdit);

        assertTrue(res);
    }

    @Test
    public void editEventWithInvitees() {

        Event e = new Event("Test Event", 1, "Test Event Description", "9-8-2022-12:12", "Test Location", new HashMap<String, Integer>(), new ArrayList<Integer>(), 0, true, true, "12hr");
        List<Integer> participants = new ArrayList<>();
        e.setEvtParticipants(participants);
        List<Integer> inviteeList = new ArrayList<>();
        inviteeList.add(1);
        inviteeList.add(2);

        boolean isEdit = false;

        createEvent.changeEventData(e, inviteeList, isEdit);

        isEdit = true;

        e.setEvtName("Test Edit Event");

        boolean res = createEvent.changeEventData(e, inviteeList, isEdit);

        assertTrue(res);
    }


    @After
    public void tearDown() throws Exception {
        createEvent = null;
    }

}
