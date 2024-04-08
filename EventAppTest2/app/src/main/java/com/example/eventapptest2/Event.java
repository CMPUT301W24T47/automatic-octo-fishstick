package com.example.eventapptest2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a event in the app.
 * Each event has a event name, location, date, capacity of attendees, event ID, event poster, etc.
 */
public class Event {
    // Attributes of the event
    String eventName;
    String eventLocation;
    String eventDate;
    String eventLimit;
    String Eventid;
    String EventDesription;
    String eventPoster;
    String QrUrl;
    String signINQR;
    ArrayList<User> attendeList;
    ArrayList<User> checkedinList;
    String owner;
    String tracking;

    /**
     * Constructor of a new event when created.
     *
     * @param eventid The ID of the event.
     * @param eventName The name of the event.
     * @param eventLocation The location of the event.
     * @param eventDate The date when the event occurs.
     * @param eventLimit The capacity of the event, limits the amount of attendees that can join.
     * @param eventPoster The event poster, which is a image.
     * @param EventDesription The description of the event.
     * @param attendelist The list of attendees in the event.
     * @param checkedinlist The list of attendees who have checed in to the event.
     * @param qrUrl The QR code URL for the event.
     * @param ss The QR code URL for attendee sign in.
     * @param id The event owners device ID.
     * @param track The tracking status of the event
     */
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventLimit, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl,String ss,String id,String track ) {
        //will need to add a notfications list
        //each event hashed by event id
        // id+attendee id+checkin id id+notfic
        this.Eventid = eventid;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = eventLimit;
        this.eventPoster = eventPoster;
        this.EventDesription = EventDesription;
        this.attendeList = attendelist;
        this.checkedinList = checkedinlist;
        this.QrUrl = qrUrl;
        this.signINQR =ss;
        this.owner = id;
        this.tracking = track;
    }
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl,String ss,String id,String track) {
        //remove the two users list from this and make this constructer "attendee event" this is meant for sign-up, so it will not a notfication list but has no care for other attendees
        this.Eventid = eventid;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = "-1";
        this.eventPoster = eventPoster;
        this.EventDesription = EventDesription;
        this.attendeList = attendelist;
        this.checkedinList = checkedinlist;
        this.QrUrl = qrUrl;
        this.signINQR =ss;
        this.owner = id;
        this.tracking = track;

    }

    public Event() {
        this.eventName = "eventName";
        this.eventLocation = "eventLocation";
        this.eventDate = "eventDate";
        this.eventLimit = "-1";
        this.eventPoster = null;
        this.EventDesription = "EventDesription";
        this.QrUrl = "qrUrl";
        this.signINQR ="ss";
        this.owner = "0";
    }

    // Constructor for admin view all events
    public Event(String eventDate, String eventDescription, String eventLimit, String eventLocation, String eventName, String eventPoster, String eventID, String eventOwner) {
        this.eventDate = eventDate;
        this.EventDesription = eventDescription;
        this.eventLimit = eventLimit;
        this.eventLocation = eventLocation;
        this.eventName = eventName;
        this.eventPoster = eventPoster;
        this.Eventid = eventID;
        this.owner = eventOwner;
    }

    // New constructor that for view all images
    // Constructor is for images only (Admin)
    public Event(String eventPoster, String ownerID, String eventId) {
        this.eventPoster = eventPoster;
        this.owner = ownerID; // User
        this.Eventid = eventId; // us

    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }
    public String getEventName() {
        return eventName;
    }

    public String getQrUrl() {
        return QrUrl;
    }

    public void setQrUrl(String qrUrl) {
        QrUrl = qrUrl;
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

    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }

    public String getEventPoster() {
        return eventPoster;
    }

    public String getEventDesription() {
        return EventDesription;
    }
    public String getEventid() {
        return Eventid;
    }

    public void setEventDesription(String eventDesription) {
        EventDesription = eventDesription;
    }


    public ArrayList<User> getAttendeList() {
        return attendeList;
    }

    public void setAttendeList(ArrayList<User> attendeList) {
        this.attendeList = attendeList;
    }

    public ArrayList<User> getCheckedinList() {
        return checkedinList;
    }

    public void setCheckedinList(ArrayList<User> checkedinList) {
        this.checkedinList = checkedinList;
    }

    public String getSignINQR() {
        return signINQR;
    }

    public void setSignINQR(String signINQR) {
        this.signINQR = signINQR;
    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

