package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
@LargeTest
public class ShareQRTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testShareQRCode() {
        // navigate to organize events
        onView(withId(R.id.OrganizeEventNav)).perform(click());
        // make mock event
        Event mockEvent = new Event("mockEventId", "Mock Event Name", "Mock Location", "20240409", "100",
                "mockPoster.jpg", "Mock Description", new ArrayList<>(), new ArrayList<>(), "mockQrUrl",
                "mockSS", "mockId", "mockTrack");
        // tap event open button
        onView(withId(R.id.OpenEventButton)).perform(click());
        // tap share
        onView(withId(R.id.ShareQRIconOrgdet)).perform(click());
        // Check if an intent to share was sent
        intended(allOf(
                hasAction(Intent.ACTION_SEND),
                hasType("image/jpeg"),
                hasExtra(Intent.EXTRA_STREAM, mockEvent.getEventPoster()),
                hasExtra(Intent.EXTRA_TEXT, mockEvent.getEventName()),
                hasExtra(Intent.EXTRA_SUBJECT, "")
        ));

    }
}
