package com.example.eventapptest2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EditUserProfileActivityTest {

    @Test
    public void testActivityInitialization() {
        // dummy user info
        Bundle bundle = new Bundle();
        bundle.putString("userName", "Nana Amoh");
        bundle.putString("homepage", "namoh_homepage");
        bundle.putString("email", "namoh@ualberta.ca");
        bundle.putString("phoneNum", "4737373");

        // launch activity with dummy info
        ActivityScenario<EditUserProfileActivity> scenario = ActivityScenario.launch(getEditUserProfileIntent(bundle));

        // check that activity launched
        scenario.onActivity(activity -> {
            assertNotNull(activity);
            // check that info is correct
            EditText editName = activity.findViewById(R.id.userEditNameText);
            EditText editHomePage = activity.findViewById(R.id.userEditHomepageText);
            EditText editEmail = activity.findViewById(R.id.userEditEmailText);
            EditText editPhoneNum = activity.findViewById(R.id.userEditPhoneNumText);

            assertEquals("Nana Amoh", editName.getText().toString());
            assertEquals("namoh_homepage", editHomePage.getText().toString());
            assertEquals("namoh@ualberta.ca", editEmail.getText().toString());
            assertEquals("4737373", editPhoneNum.getText().toString());
        });
    }

    @Test
    public void testUpdateUserProfile() {
        // activity launch
        ActivityScenario<EditUserProfileActivity> scenario = ActivityScenario.launch(EditUserProfileActivity.class);

        // user info update
        onView(withId(R.id.userEditNameText)).perform(typeText("Kofi Amoh"));
        onView(withId(R.id.userEditHomepageText)).perform(typeText("kamoh_homepage"));
        onView(withId(R.id.userEditEmailText)).perform(typeText("kamoh@ualberta.ca"));
        onView(withId(R.id.userEditPhoneNumText)).perform(typeText("4672001"));

        // tap verify button
        onView(withId(R.id.button_verify)).perform(click());

        // check user info was passed right
        scenario.onActivity(activity -> {
            Intent resultIntent = activity.getResultIntent();
            assertNotNull(resultIntent);
            assertEquals("Kofi Amoh", resultIntent.getStringExtra("updatedUserName"));
            assertEquals("kamoh_homepage", resultIntent.getStringExtra("updatedUserHomepage"));
            assertEquals("kamoh@ualberta.ca", resultIntent.getStringExtra("updatedUserEmail"));
            assertEquals("4672001", resultIntent.getStringExtra("updatedUserPhoneNum"));
        });
    }

    // create intent
    private Intent getEditUserProfileIntent(Bundle bundle) {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getContext(), EditUserProfileActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}
