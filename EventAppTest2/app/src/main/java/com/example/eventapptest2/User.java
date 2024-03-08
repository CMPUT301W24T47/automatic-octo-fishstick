package com.example.eventapptest2;

import android.media.Image;

import java.util.ArrayList;

/**
 * this class represents a user object
 */
public class User {
    String userName;
    String userHomepage;
    String userEmail;
    String userPhoneNumber;
    Image userProfileImage;
    ArrayList<Event> savedEvents;
    ArrayList<Event> createdEvents;
    ArrayList<Event> oldQRList;



    String DeviceId; // wont have a setter b/c should only every be init at start of code



    int lastsaved; // goes in databse but should never be accesed through db //it will always be set first in code b4 we get it

    /**
     * constructor with no position saved in the activity
     * @param deviceId
     * @param userName
     * @param userHomepage
     * @param userEmail
     * @param userPhoneNumber
     * @param userProfileImage
     * @param savedEvents
     * @param createdEvents
     * @param oldQRList
     */
    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, Image userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList) {
        this.DeviceId = deviceId;
        this.userName = userName;
        this.userHomepage = userHomepage;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfileImage = userProfileImage;
        this.savedEvents = savedEvents;
        this.createdEvents = createdEvents;
        this.oldQRList = oldQRList;
        this.lastsaved = 0;
    }

    /**
     * constructor with a position where they last clicked in the activity
     * @param deviceId
     * @param userName
     * @param userHomepage
     * @param userEmail
     * @param userPhoneNumber
     * @param userProfileImage
     * @param savedEvents
     * @param createdEvents
     * @param oldQRList
     * @param pos
     */
    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, Image userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList,int pos) {
        this.DeviceId = deviceId;
        this.userName = userName;
        this.userHomepage = userHomepage;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfileImage = userProfileImage;
        this.savedEvents = savedEvents;
        this.createdEvents = createdEvents;
        this.oldQRList = oldQRList;
        this.lastsaved = pos;
    }

    /**
     * constructor
     */
    public User() {

    }

    /**
     * sets the username given by the user
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets the username of the user
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the users Homepage
     * @param userHomepage
     */
    public void setUserHomepage(String userHomepage) {
        this.userHomepage = userHomepage;
    }

    /**
     * gets the users homepage
     * @return users homepage
     */
    public String getUserHomepage() {
        return userHomepage;
    }

    /**
     * sets the users email
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * gets the users email
     * @return users email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * sets the users phonenumber
     * @param userPhoneNumber
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     * gets the users phone number
     * @return users phonenumber
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     * sets the users profile pic
     * @param userProfileImage
     */
    public void setUserProfileImage(Image userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    /**
     * gets users profile pic
     * @return users profile pic
     */
    public Image getUserProfileImage() {
        return userProfileImage;
    }

    /**
     * sets a list of events saved by the user
     * @param savedEvents
     */
    public void setSavedEvents(ArrayList<Event> savedEvents) {
        this.savedEvents = savedEvents;
    }

    /**
     * gets the list of the users saved events
     * @return list of saved events
     */
    public ArrayList<Event> getSavedEvents() {
        return savedEvents;
    }

    /**
     * sets a new event created list for the organizers events
     * @param createdEvents
     */
    public void setCreatedEvents(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    /**
     * gets the list of events created by the organizer
     * @return list of created events by the user(organizer)
     */
    public ArrayList<Event> getCreatedEvents() {
        return createdEvents;
    }

    /**
     * sets the lists of old QRs from past events
     * @param oldQRList
     */
    public void setOldQRList(ArrayList<Event> oldQRList) {
        this.oldQRList = oldQRList;
    }

    /**
     * gets the list of oldQRs used
     * @return list of QRs of past events
     */
    public ArrayList<Event> getOldQRList() {
        return oldQRList;
    }

    /**
     * gets the device id of the user
     * @return users device id
     */
    public String getDeviceId() {
        return DeviceId;
    }

    /**
     * returns the position the user was last when going back from a fragment
     * @return postion last left off after leaving a fragment
     */
    public int getLastsaved() {
        return lastsaved;
    }

    /**
     * sets the where the user was last left off after a new fragment
     * @param lastsaved
     */
    public void setLastsaved(int lastsaved) {
        this.lastsaved = lastsaved;
    }
}
