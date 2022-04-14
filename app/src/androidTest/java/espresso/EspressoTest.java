package espresso;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
//import android.support.test.rule.ActivityTestRule;
import androidx.test.rule.ActivityTestRule;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

import com.example.easyteamup.MainActivity;
import com.example.easyteamup.R;
import com.example.easyteamup.SignUpPop;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        // Type in user email
        onView(withId(R.id.email))
                .perform(click(), typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        // Type in user password
        onView(withId(R.id.password))
                .perform(click(), typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        // click login button
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void emailLogin() {
        // Type in user email
        onView(withId(R.id.email))
                .perform(click(), typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        // Check that the user email was recorded.
        onView(withId(R.id.email)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void switchToMain() {
        // Click the Login/Signup button
        onView(withId(R.id.loginButton)).perform(click());

        // Check that it switches to the main page with the "addEventButton"
        onView(withId(R.id.addEventButton)).check(matches(isDisplayed()));
    }

    @Test
    public void createEvent() {
        onView(withId(R.id.addEventButton)).perform(click());
        // Type in the name of the event to create
        onView(withId(R.id.newEvtName))
                .perform(click(), typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        // Check that the event was recorded.
        onView(withId(R.id.newEvtName)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void changeProfile() {
        // Type in the user first name
        onView(withId(R.id.et_first_name))
                .perform(click(), typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        // Check that the profile name is changed.
        onView(withId(R.id.et_first_name)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void changeProfilePicture() {
        // Try to change the profile picture via clicking it
        onView(withId(R.id.imageView)).perform(click()).check(matches(isClickable()));
    }

    @Test
    public void selectTime() {
        // Click any event
        onView(withId(R.id.eventsListView)).perform(click());

        // The user should be able to select an available time slot
        onView(withId(R.id.selectTimeSlots)).check(matches(isDisplayed()));
    }

    @Test
    public void clickTab() {
        // The tab bar on the profile page should be clickable
        onView(withId(R.id.tabLayout)).perform(click()).check(matches(isClickable()));
    }

    @Test
    public void updateProfile() {
        // By clicking on the update profile button the user should go back to the main page
        onView(withId(R.id.bt_register)).perform(click());
        onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEventCategory() {
        // click on an item of type String in a spinner
        // afterwards verify that the view with the R.id.newEvtTypePicker id contains "Sports"
        onView(withId(R.id.newEvtTypePicker)).perform(click())
                .check(matches(withText(containsString("Sports"))));
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void checkToast() throws Exception {
//        onView(withId(R.id.change_profile)).perform(click());
//
//        onView(withId(R.id.imageView3)).perform(click());
//        onView(withText("Avatar Updated!"))
//                .inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView()))))
//                .check(matches(isDisplayed()));
//    }

}
