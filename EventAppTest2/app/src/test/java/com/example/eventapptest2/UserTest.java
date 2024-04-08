package com.example.eventapptest2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class UserTest {
    @Test
    public void testSetAndGetUserName(){
        User testUser = new User();
        testUser.setUserName("namoh");
        assertEquals("namoh", testUser.getUserName());
    }
    @Test
    public void testSetAndGetUserHomePage(){
        User testUser = new User();
        testUser.setUserHomepage("namoh-homepage");
        assertEquals("namoh-homepage", testUser.getUserHomepage());
    }
    @Test
    public void testSetAndGetUserEmail() {
        User testUser = new User();
        testUser.setUserEmail("namoh@ualberta.ca");
        assertEquals("namoh@ualberta.ca", testUser.getUserEmail());
    }

    @Test
    public void testSetAndGetUserPhoneNumber() {
        User testUser = new User();
        testUser.setUserPhoneNumber("473-7373");
        assertEquals("473-7373", testUser.getUserPhoneNumber());
    }

    @Test
    public void testSetAndGetUserProfileImage() {
        User testUser = new User();
        testUser.setUserProfileImage("profilePic.jpg");
        assertEquals("profilePic.jpg", testUser.getUserProfileImage());
    }

    @Test
    public void testSetAndGetSavedEvents() {
        User testUser = new User();
        Event testEvent1 = new Event();
        Event testEvent2 = new Event();
        ArrayList<Event> events = new ArrayList<>();
        events.add(testEvent1);
        events.add(testEvent2);
        testUser.setSavedEvents(events);
        assertEquals(events, testUser.getSavedEvents());
    }

    @Test
    public void testSetAndGetCreatedEvents() {
        User testUser = new User();
        Event testEvent1 = new Event();
        Event testEvent2 = new Event();
        ArrayList<Event> events = new ArrayList<>();
        events.add(testEvent1);
        events.add(testEvent2);
        testUser.setCreatedEvents(events);
        assertEquals(events, testUser.getCreatedEvents());
    }

    @Test
    public void testSetAndGetOldQRList() {
        User testUser = new User();
        Event testEvent1 = new Event();
        Event testEvent2 = new Event();
        ArrayList<Event> events = new ArrayList<>();
        events.add(testEvent1);
        events.add(testEvent2);
        testUser.setOldQRList(events);
        assertEquals(events, testUser.getOldQRList());
    }

    @Test
    public void testSetAndGetLastSaved() {
        User testUser = new User();
        testUser.setLastsaved(5);
        assertEquals(5, testUser.getLastsaved());
    }

    @Test
    public void testSetAndGetCheckInCount() {
        User testUser = new User();
        testUser.setCheckInCount("5");
        assertEquals("5", testUser.getCheckInCount());
    }
}