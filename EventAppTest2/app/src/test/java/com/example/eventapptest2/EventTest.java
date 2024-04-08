package com.example.eventapptest2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class EventTest{
    private Event testEvent;
    @Before
    public void createTestEvent(){
        ArrayList<User> testAttendeeList = new ArrayList<>();
        ArrayList<User> testCheckedInList = new ArrayList<>();
        testEvent = new Event("testEventID", "testEventName","testLocation",
                "2024-03-08", "10", "testPoster.jpg",
                "testDescription", testAttendeeList, testCheckedInList,
                "testQrURL", "testSS", "testID", "testTrack");
    }
    @Test
    public void testEventExists() {
        // make sure event exists
        assertNotNull(testEvent);
    }
    @Test
    public void testEventNameSetAndGet() {
        // check event name
        assertEquals("testEventName", testEvent.getEventName());
    }
    @Test
    public void testEventLocationSetAndGet() {
        // check event location
        assertEquals("testLocation", testEvent.getEventLocation());
    }
    @Test
    public  void testEventDateSetAndGet() {
        // check event date
        assertEquals("2024-03-08", testEvent.getEventDate());
    }
    @Test
    public void testEventLimitSetAndGet() {
        // check event limit
        assertEquals("10", testEvent.getEventLimit());
    }
    @Test
    public void testEventPosterSetAndGet() {
        // check poster
        assertEquals("testPoster.jpg", testEvent.getEventPoster());
    }
    @Test
    public void testEventDescriptionSetAndGet(){
        // check description
        assertEquals("testDescription", testEvent.getEventDesription());
    }
    @Test
    public void testEventAttendeeList(){
        User testUser1 = new User();
        User testUser2 = new User();
        testEvent.attendeList.add(testUser1);
        testEvent.attendeList.add(testUser2);
        assertEquals(testEvent.attendeList, testEvent.getAttendeList());
    }
    @Test
    public void testEventCheckedInList(){
        User testUser1 = new User();
        testEvent.checkedinList.add(testUser1);
        assertEquals(testEvent.checkedinList, testEvent.getCheckedinList());
    }
    @Test
    public void testEventQrURLSetAndGet(){
        testEvent.setQrUrl("testQRUrl");
        assertEquals("testQRUrl", testEvent.getQrUrl());
    }
    @Test
    public void testEventSSSetAndGet(){
        testEvent.setSignINQR("testSS");
        assertEquals("testSS", testEvent.getSignINQR());
    }

    @Test
    public void testEventTrackSetAndGet(){
        testEvent.setTracking("testTrack");
        assertEquals("testTrack", testEvent.getTracking());
    }
}

