package com.example.eventapptest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SavedEventsFragmentTest {

    private ArrayList<Event> savedEvents;

    @Before
    public void createTestSavedEvents() {
        // create mock events
        savedEvents = new ArrayList<>();
        savedEvents.add(new Event("Event 1"));
        savedEvents.add(new Event("Event 2"));
    }

    @Test
    public void testFragmentInitialization() {
        // create a new activity scenario
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        // start fragment instance
        SavedEventsFragment fragment = new SavedEventsFragment(savedEvents);

        // start fragment transaction
        activityScenario.onActivity(activity -> {
            activity.getSupportFragmentManager().beginTransaction().add(fragment, null).commitNow();
        });

        // check fragment is visible
        onView(withId(R.id.saved_events_list)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }
}
