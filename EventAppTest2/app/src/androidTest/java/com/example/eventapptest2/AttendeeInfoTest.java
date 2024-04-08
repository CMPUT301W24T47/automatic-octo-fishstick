package com.example.eventapptest2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;
// handles US02.02.01, US02.02.02, US02.02.03, US02.050.01
@RunWith(JUnit4.class)
@LargeTest
public class AttendeeInfoTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }
    @Test
    public void editAttendeeInfoTest(){
        // navigate to the profile section
        onView(withId(R.id.AttendeeProfile)).perform(click());
        // click on replace profile button
        onView(withId(R.id.replaceImageButton)).perform(click());
        //
        // startt intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intents.intending(hasAction(Intent.ACTION_PICK)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, intent));

        // Perform click again on replaceImageButton to trigger image selection from gallery
        onView(withId(R.id.replaceImageButton)).perform(click());

        // Check if the intent to pick image from gallery is sent
        Intents.intended(allOf(
                hasAction(Intent.ACTION_PICK),
                hasType("image/*"),
                hasCategories(Collections.singleton(Intent.CATEGORY_OPENABLE)),
                hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        ));


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
        // check info is as it should be
        onView(withText("Test Username")).check(matches(isDisplayed()));
        onView(withText("Test Homepage")).check(matches(isDisplayed()));
        onView(withText("testEmail@ualberta.ca")).check(matches(isDisplayed()));
        onView(withText("4737373")).check(matches(isDisplayed()));
        // delete profile pic
        onView(withId(R.id.deleteImageButton)).perform(click());
        // ensure there's no picture there
        onView(withId(R.id.userImage)).check(doesNotExist());
        // ensure that profile picture is now set to be "first name"
        onView(withText("Test")).check(matches(isDisplayed()));
    }
}
