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
// handles US 02.07.01, US 02.08.01, US 02.09.01
@RunWith(JUnit4.class)
@LargeTest

public class AttendeeSignUpTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    public void setUpUser(){
        // edit username
        onView(withId(R.id.editUsernameButton)).perform(click());
        onView(withId(R.id.userEditNameText)).perform(ViewActions.typeText("Test Username"));
        // edit homepage
        onView(withId(R.id.userEditHomepageText)).perform(ViewActions.typeText("Test Homepage"));
        // edit email
        onView(withId(R.id.userEditEmailText)).perform(ViewActions.typeText("testEmail@ualberta.ca"));
        // click on phone number
        onView(withId(R.id.userEditPhoneNumText)).perform(ViewActions.typeText("4737373"));
        // verify info
        onView(withId(R.id.button_verify)).perform(click());
    }
    @Test
    public void attendeeSignUp(){
        setUpUser();
        // navigate to explore events page
        onView(withId(R.id.ExploreEventNav)).perform(click());
        // click on the details of an event
        onView(withId(R.id.ExploreEventDetialsButton)).perform(click());
        // sign up for event
        onView(withId(R.id.QRSignUpbut)).perform(click());
        // navigate to saved events
        onView(withId(R.id.SavedEventNav)).perform(click());
        // Check if the saved events list contains items
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }
}
