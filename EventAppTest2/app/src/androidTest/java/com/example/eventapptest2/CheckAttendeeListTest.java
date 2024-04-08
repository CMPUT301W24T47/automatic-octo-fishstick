package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
// handles US01.02.01

@RunWith(JUnit4.class)
@LargeTest
public class CheckAttendeeListTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    // Setup function to create an event
    public void setupCreateEvent() {
        // Click create event button
        onView(withId(R.id.AddEventnav)).perform(click());

        // Add event information
        onView(withId(R.id.DescriptionText)).perform(ViewActions.typeText("testEventName"));
        onView(withId(R.id.add_location)).perform(ViewActions.typeText("Location"));
        onView(withId(R.id.add_attendee_limit)).perform(ViewActions.typeText("100"));
        onView(withId(R.id.addEventDate)).perform(ViewActions.typeText("10082024"));
        onView(withId(R.id.AddEventDescription)).perform(ViewActions.typeText("Celebrating a birthday!"));

        // Adding image (if required)
        onView(withId(R.id.addEventImageButton)).perform(click());

        // Confirm event creation
        onView(withId(R.id.AddEventconfirmButton)).perform(click());
    }
    @Test
    public void checkAttendeeList(){
        // setup, create the event
        setupCreateEvent();
        // go back
        onView(withId(R.id.GoBacknav)).perform(click());
        // click events
        onView(withId(R.id.OrganizeEventNav)).perform(click());
        // tap event open button
        onView(withId(R.id.OpenEventButton)).perform(click());
        // tap attendee list
        onView(withId(R.id.EventAttendeesNav)).perform(click());
        // make sure the right thing is being looked at
        onView(withId(R.id.EventAttendeesNav)).check(matches(isDisplayed()));
    }
}
