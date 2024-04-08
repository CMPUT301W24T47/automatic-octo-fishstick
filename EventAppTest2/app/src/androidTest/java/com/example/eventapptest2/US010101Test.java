package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

import android.content.Intent;
import android.provider.MediaStore;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// US 01.01.01 As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.
@RunWith(JUnit4.class)
@LargeTest
public class US010101Test {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateEvent() {
        // click create event button
        // add some info
        onView(withId(R.id.AddEventnav)).perform(click());
        onView(withId(R.id.DescriptionText)).perform(ViewActions.typeText("testEventName"));
        onView(withId(R.id.add_location)).perform(ViewActions.typeText("Location"));
        onView(withId(R.id.add_attendee_limit)).perform(ViewActions.typeText("100"));
        onView(withId(R.id.addEventDate)).perform(ViewActions.typeText("10082024"));
        onView(withId(R.id.AddEventDescription)).perform(ViewActions.typeText("Celebrating a birthday!"));
        // adding image
        onView(withId(R.id.addEventImageButton)).perform(click());
        // confirm
        onView(withId(R.id.AddEventconfirmButton)).perform(click());
        // Check if entered text  is matched with any of the text displayed on the screen
        onView(withText("testEventName")).check(matches(isDisplayed()));
        onView(withText("Celebrating a birthday!")).check(matches(isDisplayed()));
        // check if intent to pick pic is sent
        intended(allOf(hasAction(Intent.ACTION_PICK), hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
        // check qr is there
        onView(withId(R.id.QRSignUpQR)).check(matches(isDisplayed()));
    }
}