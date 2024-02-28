package com.example.eventapptest2;

import android.media.Image;

public class Event {


    String eventName;
    String eventLocation;
    String eventDate;
    int eventLimit;
    Image eventPoster;
    // QR signIn;
    // QR posterQR;

    public Event(String eventName, String eventLocation, String eventDate, int eventLimit, Image eventPoster) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = eventLimit;
        this.eventPoster = eventPoster;
    }

    public Event(String eventName, String eventLocation, String eventDate, Image eventPoster) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = -1; // we won't have a negative number of users
        this.eventPoster = eventPoster;
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

    public void setEventPoster(Image eventPoster) {
        this.eventPoster = eventPoster;
    }

    public Image getEventPoster(Image eventPoster) {
        return eventPoster;
    }
}

