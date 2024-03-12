package com.example.eventapptest2;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Event {


    String eventName;
    String eventLocation;
    String eventDate;
    String eventLimit;
    // we should have a the organizers diveiceid so we can update their event in their eventlist from firebase specifially




    String Eventid;
    String EventDesription;
    String eventPoster;



    String QrUrl;



    String signINQR;


    ArrayList<User> attendeList;
    ArrayList<User> checkedinList;


    String owner;

    // QR signIn;
    // QR posterQR;

    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventLimit, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl,String ss,String id ) {
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
    }
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl,String ss,String id) {
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

