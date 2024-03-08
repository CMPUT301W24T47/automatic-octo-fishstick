package com.example.eventapptest2;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBottomNavigationItems() {
        // check that UserProfileFragment is showing
        Espresso.onView(ViewMatchers.withId(R.id.fragment_user_profile)).check(matches(isDisplayed()));

        // tap on ExploreEventNav
        Espresso.onView(withId(R.id.ExploreEventNav)).perform(click());
        // Verify ExploreFragment is displayed
        Espresso.onView(withId(R.id.fragment_explore)).check(matches(isDisplayed()));

        // tap on SavedEventNav
        Espresso.onView(withId(R.id.SavedEventNav)).perform(click());
        // check that SavedEventsFragment is showing
        Espresso.onView(withId(R.id.fragment_saved_events)).check(matches(isDisplayed()));

        // tap on OldQrNav
        Espresso.onView(withId(R.id.OldQrNav)).perform(click());
        // check that OldQrsFragments is showing
        Espresso.onView(withId(R.id.fragment_old_qrs)).check(matches(isDisplayed()));

        // tap on AddEventnav
        Espresso.onView(withId(R.id.AddEventnav)).perform(click());
        // check AddEventFragment dialog is showing
        Espresso.onView(withId(R.id.fragment_add_event)).check(matches(isDisplayed()));
        // Dismiss the dialog
        Espresso.pressBack();

        // Click on GoBacknav
        Espresso.onView(withId(R.id.GoBacknav)).perform(click());
        // check UserProfileFragment is showing
        Espresso.onView(withId(R.id.fragment_user_profile)).check(matches(isDisplayed()));
    }
}
