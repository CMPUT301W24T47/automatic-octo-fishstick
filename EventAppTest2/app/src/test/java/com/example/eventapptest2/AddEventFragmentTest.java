package com.example.eventapptest2;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddEventFragmentTest {

    @Test
    public void testDialogFragmentDisplayed() {
        // launches fragment
        FragmentScenario<AddEventFragment> scenario = FragmentScenario.launchInContainer(AddEventFragment.class);

        // check that text is correct
        Espresso.onView(withId(R.id.fragment_add_event)).check(matches(isDisplayed()));
    }

    @Test
    public void testDialogTitle() {
        // launches fragment
        FragmentScenario<AddEventFragment> scenario = FragmentScenario.launchInContainer(AddEventFragment.class);

        // check text is correct
        Espresso.onView(withText("Add an Event")).check(matches(isDisplayed()));
    }

    @Test
    public void testPositiveButton() { // +ve button adds event
        // launches fragment
        FragmentScenario<AddEventFragment> scenario = FragmentScenario.launchInContainer(AddEventFragment.class);

        // push positive button
        Espresso.onView(withId(android.R.id.button1)).perform(ViewActions.click());

        // extra functionality later
    }

    @Test
    public void testNegativeButton() { // -ve button dcancles the addition
        // launches fragment
        FragmentScenario<AddEventFragment> scenario = FragmentScenario.launchInContainer(AddEventFragment.class);

        // pushes button
        Espresso.onView(withId(android.R.id.button2)).perform(ViewActions.click());

        // extra functionality later
    }

    @Test
    public void testEditText() {
        // launches fragment
        FragmentScenario<AddEventFragment> scenario = FragmentScenario.launchInContainer(AddEventFragment.class);

        // Type into the EditText
        Espresso.onView(withId(R.id.editText)).perform(ViewActions.typeText("Test text"));

        // check text is what it's supposed to be
        Espresso.onView(withId(R.id.editText)).check(matches(withText("Test text")));
    }
}