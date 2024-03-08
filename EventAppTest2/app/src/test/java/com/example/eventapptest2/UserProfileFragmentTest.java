package com.example.eventapptest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@Config(sdk = {Config.OLDEST_SDK, Config.NEWEST_SDK})
public class UserProfileFragmentTest {

    private UserProfileFragment fragment;
    private User testUser;

    @Before
    public void createTestUser() {
        // create a test user
        testUser = new User();
        testUser.setUserName("TestUser");

        // create the fragment instance with the test user
        fragment = new UserProfileFragment(testUser);

        // start the fragment
        FragmentTestUtil.startFragment(fragment);
    }

    @Test
    public void testFragmentNotNull() {
        assertNotNull(fragment);
    }

    @Test
    public void testUserNameDisplay() {
        // get fragment base view
        View rootView = fragment.getView();
        assertNotNull(rootView);

        // get textView for username display
        TextView usernameTextView = rootView.findViewById(R.id.usernameTextViewProfile);
        assertNotNull(usernameTextView);

        // check that username is shown correctly
        assertEquals(testUser.getUserName(), usernameTextView.getText().toString());
    }
}
