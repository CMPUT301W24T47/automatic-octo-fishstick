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



    String DeviceId; // wont have a setter b/c should only every be init at start of code



    int lastsaved; // goes in databse but should never be accesed through db //it will always be set first in code b4 we get it

    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, String userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList) {
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
    public User(String deviceId,String userName, String userHomepage, String userEmail, String userPhoneNumber, String userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList,int pos) {
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
    public User() {

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
}
