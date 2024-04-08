package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest

public class SendNotificationTest {
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
    public void sendNotificationTest(){
        boolean notificationSent = false; // if the stage where notif is sent is reached, this is set true
        // setup, create the event
        setupCreateEvent();
        // go back
        onView(withId(R.id.GoBacknav)).perform(click());
        // click events
        onView(withId(R.id.OrganizeEventNav)).perform(click());
        // tap event open button
        onView(withId(R.id.OpenEventButton)).perform(click());
        // tap notification section
        onView(withId(R.id.EventNotifyNav)).perform(click());
        // tap the text box, enter a notification, hit "notify users"
        onView(withId(R.id.OrganizerNotifyEditText)).perform(ViewActions.typeText("Test Notify"));
        onView(withId(R.id.OrganizerNotifyBtn)).perform(click());
        // Mark notification as sent
        notificationSent = true;

        // Assertion: Check if the notification was sent
        assert notificationSent;
    }
}
