package com.example.eventapptest2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

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
