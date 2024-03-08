package com.example.eventapptest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.squareup.picasso.Picasso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class OrganizeEventDetsFragmentTest {

    private Event event;

    @Before
    public void createTestEvent() {
        // create event, give it data
        event = new Event();
        event.setEventName("Test Event");
        event.setEventDate("2024-03-08");
        event.setEventLocation("Test Location");
        event.setEventDesription("Test Description");
        event.setEventLimit("100");
        event.setEventPoster("Poster string");
    }

    @Test
    public void testFragmentInitialization() {
        // create a new activity scenario
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        // instantiate fragment
        OrganizeEventDetsFragment fragment = new OrganizeEventDetsFragment(event);

        // begin fragment transaction
        activityScenario.onActivity(activity -> {
            activity.getSupportFragmentManager().beginTransaction().add(fragment, null).commitNow();
        });

        // check that fragment can be seen
        onView(withId(R.id.EventDetailNameOrgdet)).check(matches(isDisplayed()));
        onView(withId(R.id.CalenderDateOrgdet)).check(matches(isDisplayed()));
        onView(withId(R.id.LocationNameOrgdet)).check(matches(isDisplayed()));
        onView(withId(R.id.EventDescriptionOrgdet)).check(matches(isDisplayed()));
        onView(withId(R.id.AttendeeLimitNumOrgdet)).check(matches(isDisplayed()));
        onView(withId(R.id.EventImageOrgdet)).check(matches(isDisplayed()));
    }
}
