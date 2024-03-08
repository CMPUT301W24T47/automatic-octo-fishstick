package com.example.eventapptest2;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    private User user;

    @Before
    public void createTestUser() {
        // make a new user before each test
        user = new User("deviceId", "John Doe", "example.com", "john@example.com", "123456789", null,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testUserName() {
        // test setting and getting user name
        user.setUserName("Jane Doe");
        assertEquals("Jane Doe", user.getUserName());
    }

    @Test
    public void testUserHomepage() {
        // test setting and getting user homepage
        user.setUserHomepage("example.org");
        assertEquals("example.org", user.getUserHomepage());
    }

    @Test
    public void testUserEmail() {
        // test setting and getting user email
        user.setUserEmail("jane@example.com");
        assertEquals("jane@example.com", user.getUserEmail());
    }

    @Test
    public void testUserPhoneNumber() {
        // test setting and getting user phone number
        user.setUserPhoneNumber("987654321");
        assertEquals("987654321", user.getUserPhoneNumber());
    }

    @Test
    public void testUserProfileImage() {
        // test setting and getting user profile image
        Image profileImage = null; // needs actual image?
        user.setUserProfileImage(profileImage);
        assertEquals(profileImage, user.getUserProfileImage());
    }

    @Test
    public void testSavedEvents() {
        // test setting and getting saved events list
        ArrayList<Event> savedEvents = new ArrayList<>();
        savedEvents.add(new Event("Event 1"));
        user.setSavedEvents(savedEvents);
        assertEquals(savedEvents, user.getSavedEvents());
    }

    @Test
    public void testCreatedEvents() {
        // test setting and getting created events list
        ArrayList<Event> createdEvents = new ArrayList<>();
        createdEvents.add(new Event("Event 2"));
        user.setCreatedEvents(createdEvents);
        assertEquals(createdEvents, user.getCreatedEvents());
    }

    @Test
    public void testOldQRList() {
        // test setting and getting old QR list
        ArrayList<Event> oldQRList = new ArrayList<>();
        oldQRList.add(new Event("Event 3"));
        user.setOldQRList(oldQRList);
        assertEquals(oldQRList, user.getOldQRList());
    }

    @Test
    public void testDeviceId() {
        // test getting device ID
        assertEquals("deviceId", user.getDeviceId());
    }

    @Test
    public void testLastSaved() {
        // test setting and getting last saved value
        user.setLastsaved(5);
        assertEquals(5, user.getLastsaved());
    }
}
