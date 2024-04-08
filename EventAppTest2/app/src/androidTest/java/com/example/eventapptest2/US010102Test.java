package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// US 01.01.02 As an organizer, I want the option to reuse an existing QR code for attendee check-ins.
@RunWith(JUnit4.class)
public class US010102Test {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void selectOldEvent_ReusingQRCode() {
        // Launch the app and navigate to the main screen
        onView(withId(R.id.ExploreEventDetialsButtone)).perform(click());

        // Select an old event from the list
        onView(withId(R.id.ExploreEventImagee))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void confirmReuseQRCode() {
        // Launch the app and navigate to the main screen
        onView(withId(R.id.SavedEventNav)).perform(click());

        // Select an old event from the list
        onView(withId(R.id.ExploreEventImagee))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the button to reuse QR code
        onView(withId(R.id.ExploreEventDetialsButtone)).perform(click());

        // Check if the confirmation dialog is displayed
        onView(withText("Are you sure you want to reuse this QR code?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void confirmReuseQRCodeAction() {
        // Launch the app and navigate to the main screen
        onView(withId(R.id.OrginzersEventsnav)).perform(click());

        // Select an old event from the list
        onView(withId(R.id.ExploreEventImagee))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the button to reuse QR code
        onView(withId(R.id.ExploreEventDetialsButtone)).perform(click());

        // Click on the confirmation button to reuse the QR code
        onView(withText("Confirm")).inRoot(isDialog()).perform(click());
    }
}
