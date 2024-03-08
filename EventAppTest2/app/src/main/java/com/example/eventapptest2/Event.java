package com.example.eventapptest2;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an event
 */
public class Event {


    String eventName;
    String eventLocation;
    String eventDate;
    String eventLimit;
    // we should have a the organizers diveiceid so we can update their event in their eventlist from firebase specifially




    String Eventid;
    String EventDesription;
    String eventPoster;



    ArrayList<User> attendeList;
    ArrayList<User> checkedinList;

    // QR signIn;
    // QR posterQR;

    /**
     * constructor that is made with an attendee limit
     * @param eventid
     * @param eventName
     * @param eventLocation
     * @param eventDate
     * @param eventLimit
     * @param eventPoster
     * @param EventDesription
     * @param attendelist
     * @param checkedinlist
     */
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventLimit, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist ) {
        this.Eventid = eventid;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = eventLimit;
        this.eventPoster = eventPoster;
        this.EventDesription = EventDesription;
        this.attendeList = attendelist;
        this.checkedinList = checkedinlist;
    }

    /**
     * constructor that is made without an attendee limit
     * @param eventid
     * @param eventName
     * @param eventLocation
     * @param eventDate
     * @param eventPoster
     * @param EventDesription
     * @param attendelist
     * @param checkedinlist
     */
    public Event(String eventid, String eventName, String eventLocation, String eventDate, String eventPoster,String EventDesription,ArrayList<User> attendelist, ArrayList<User> checkedinlist ) {
        this.Eventid = eventid;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLimit = "-1";
        this.eventPoster = eventPoster;
        this.EventDesription = EventDesription;
        this.attendeList = attendelist;
        this.checkedinList = checkedinlist;
    }

    /**
     * empty event
     */
    public Event() {
        this.eventName = "eventName";
        this.eventLocation = "eventLocation";
        this.eventDate = "eventDate";
        this.eventLimit = "-1";
        this.eventPoster = null;
        this.EventDesription = "EventDesription";
    }

    /**
     * gets events name
     * @return the name of the event
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * sets event name
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * gets the event location
     * @return the location of the event
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * sets the events location
     * @param eventLocation
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * gets the event date
     * @return the date of the event
     */
    public String getEventDate() {
        return eventDate;
    }

    /**
     * sets the events date
     * @param eventDate
     */
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * gets events attendee limit
     * @return the events capacity
     */
    public String getEventLimit() {
        return eventLimit;
    }

    /**
     * sets event attendence limit
     * @param eventLimit
     */
    public void setEventLimit(String eventLimit) {
        this.eventLimit = eventLimit;
    }

    /**
     * sets event poster
     * @param eventPoster
     */
    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }

    /**
     * gets the image of the event
     * @return poster of event
     */
    public String getEventPoster() {
        return eventPoster;
    }

    /**
     * gets the events description
     * @return the description of the event
     */
    public String getEventDesription() {
        return EventDesription;
    }

    /**
     * sets the events unique id
     * @return the id made for the event
     */
    public String getEventid() {
        return Eventid;
    }

    /**
     * sets the events description
     * @param eventDesription
     */
    public void setEventDesription(String eventDesription) {
        EventDesription = eventDesription;
    }

    /**
     * gets teh list of attendees
     * @return the list of attendee of the event
     */
    public ArrayList<User> getAttendeList() {
        return attendeList;
    }

    /**
     * sets the attendee list for the event
     * @param attendeList
     */
    public void setAttendeList(ArrayList<User> attendeList) {
        this.attendeList = attendeList;
    }

    /**
     * gets the amount of times the user has checked in to the event
     * @return an array of how many times a user has check in to the event
     */
    public ArrayList<User> getCheckedinList() {
        return checkedinList;
    }

    /**
     * sets the array of how many times each user has checked in
     * @param checkedinList
     */
    public void setCheckedinList(ArrayList<User> checkedinList) {
        this.checkedinList = checkedinList;
    }

    /**
     * stores an objects data in a hashmap
     * @return a the event data of the event
     */
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

