package com.example.eventapptest2;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String userName;
    String userHomepage;
    String userEmail;
    String userPhoneNumber;
    String userProfileImage;
    ArrayList<Event> savedEvents;
    ArrayList<Event> createdEvents;
    ArrayList<Event> oldQRList;



    String CheckInCount;

    String DeviceId; // wont have a setter b/c should only every be init at start of code

    String userLongitude;
    String userLatitude;

    String tracking;



    int lastsaved; // goes in databse but should never be accesed through db //it will always be set first in code b4 we get it

    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, String userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList,String track) {
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
        this.tracking = track;
    }
    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, String userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList,int pos,String track) {
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
        this.tracking = track;

    }
    public User(String AttendeeName,String AttendeeImage, String CheckInCount, String userLongitude, String userLatitude) {
        this.userName = AttendeeName;
        this.userProfileImage = AttendeeImage;
        this.CheckInCount = CheckInCount;
        this.userLongitude = userLongitude;
        this.userLatitude = userLatitude;
    }
    public User() {

    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public String getUserLatitude() {
        return userLatitude;
    }
    // Constructor for Admin user profile
    public User(String deviceId,String userName, String userImage, String userHomepage, String userEmail, String userPhoneNumber) {
        this.DeviceId = deviceId;
        this.userName = userName;
        this.userProfileImage = userImage;
        this.userHomepage = userHomepage;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;

    }


    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }


    public void setUserHomepage(String userHomepage) {
        this.userHomepage = userHomepage;
    }
    public String getUserHomepage() {
        return userHomepage;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail() {
        return userEmail;
    }


    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }


    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
    public String getUserProfileImage() {
        return userProfileImage;
    }


    public void setSavedEvents(ArrayList<Event> savedEvents) {
        this.savedEvents = savedEvents;
    }

    public ArrayList<Event> getSavedEvents() {
        return savedEvents;
    }
    public void setCreatedEvents(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }
    public ArrayList<Event> getCreatedEvents() {
        return createdEvents;
    }
    public void setOldQRList(ArrayList<Event> oldQRList) {
        this.oldQRList = oldQRList;
    }
    public ArrayList<Event> getOldQRList() {
        return oldQRList;
    }
    public String getDeviceId() {
        return DeviceId;
    }
    public int getLastsaved() {
        return lastsaved;
    }

    public void setLastsaved(int lastsaved) {
        this.lastsaved = lastsaved;
    }
    public String getCheckInCount() {
        return CheckInCount;
    }

    public void setCheckInCount(String checkInCount) {
        CheckInCount = checkInCount;
    }
}
