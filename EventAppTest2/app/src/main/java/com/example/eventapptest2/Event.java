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


    ArrayList<User> attendeList;
    ArrayList<User> checkedinList;

    // QR signIn;
    // QR posterQR;

    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventLimit, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl ) {
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
    }
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist,String qrUrl ) {
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
    }

    public Event() {
        this.eventName = "eventName";
        this.eventLocation = "eventLocation";
        this.eventDate = "eventDate";
        this.eventLimit = "-1";
        this.eventPoster = null;
        this.EventDesription = "EventDesription";
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

    public Map<String, Object> toMap() {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("eventId", Eventid);
        eventData.put("eventName", eventName);
        eventData.put("eventLocation", eventLocation);
        eventData.put("eventDate", eventDate);
        eventData.put("eventLimit", eventLimit);
        eventData.put("eventDescription", EventDesription);
        eventData.put("eventStringURL",eventPoster);
        eventData.put("attendeeList",attendeList);
        eventData.put("Checkin-list",checkedinList);
        return eventData;
    }
}

