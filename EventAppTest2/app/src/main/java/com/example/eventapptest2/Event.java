package com.example.eventapptest2;

import android.media.Image;
import android.widget.ImageView;

public class Event {


    String eventName;
    String eventLocation;
    String eventDate;
    String eventLimit; //should be int
    ImageView eventPoster;
    // QR signIn;
    // QR posterQR;
    String details;

    public Event(String eventName, String eventLocation, String eventDate, String eventLimit, ImageView eventPoster, String details) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = eventLimit;
        this.eventPoster = eventPoster;
        this.details = details;
    }

    public Event(String eventName, String eventLocation, String eventDate, ImageView eventPoster, String details) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = "-1"; // we won't have a negative number of users
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

    public String getEventLimit() {
        return eventLimit;
    }

    public void setEventLimit(String eventLimit) {
        this.eventLimit = eventLimit;
    }

    public void setEventPoster(ImageView eventPoster) {
        this.eventPoster = eventPoster;
    }

    public ImageView getEventPoster(ImageView eventPoster) {
        return eventPoster;
    }

    public String getDetails() {return details;}

    public void  setDetails(String details) {this.details = details;}
}


