package com.example.eventapptest2;


import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;

public class Event {


    String eventName;
    String eventLocation;
    String eventDate;
    int eventLimit;
    Uri eventPoster;
    // QR signIn;
    // QR posterQR;
    String details;


    ArrayList<User> attendeeList;

    public Event(String eventName, String eventLocation, String eventDate, int eventLimit, Uri eventPoster, String details) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = eventLimit;
        this.eventPoster = eventPoster;
        this.details = details;
    }

    public Event(String eventName, String eventLocation, String eventDate, Uri eventPoster, String details) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = -1; // we won't have a negative number of users
        this.eventPoster = eventPoster;
        this.details = details;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventLimit() {
        return eventLimit;
    }

    public void setEventLimit(int eventLimit) {
        this.eventLimit = eventLimit;
    }

    public void setEventPoster(Uri eventPoster) {
        this.eventPoster = eventPoster;
    }

    public Uri getEventPoster(Uri eventPoster) {
        return eventPoster;
    }

    public ArrayList<User> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(ArrayList<User> attendeeList) {
        this.attendeeList = attendeeList;
    }
}

