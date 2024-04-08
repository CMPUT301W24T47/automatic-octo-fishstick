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

    /**
     * User information including many different details pertaining to a unique user
     * @param deviceId
     * @param userName
     * @param userHomepage
     * @param userEmail
     * @param userPhoneNumber
     * @param userProfileImage
     * @param savedEvents
     * @param createdEvents
     * @param oldQRList
     * @param track
     */
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

    /**
     * Unique paramter pos
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
     * @param track
     */
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

    /**
     * unique paramters longitude and latitude, for saving user location
     * @param deviceId
     * @param deviceIds
     * @param AttendeeName
     * @param AttendeeImage
     * @param CheckInCount
     * @param userLongitude
     * @param userLatitude
     */
    public User(String deviceId,String deviceIds,String AttendeeName,String AttendeeImage, String CheckInCount, String userLongitude, String userLatitude) {
        this.DeviceId = deviceId;
        this.userName = AttendeeName;
        this.userProfileImage = AttendeeImage;
        this.CheckInCount = CheckInCount;
        this.userLongitude = userLongitude;
        this.userLatitude = userLatitude;
    }
    public User() {

    }

    /**
     *
     * @return userLongitude
     */
    public String getUserLongitude() {
        return userLongitude;
    }

    /**
     *
     * @return userLatitude
     */
    public String getUserLatitude() {
        return userLatitude;
    }

    /**
     * Constructor for Admin user profile
     * @param deviceId
     * @param userName
     * @param userImage
     * @param userHomepage
     * @param userEmail
     * @param userPhoneNumber
     */
    public User(String deviceId,String userName, String userImage, String userHomepage, String userEmail, String userPhoneNumber) {
        this.DeviceId = deviceId;
        this.userName = userName;
        this.userProfileImage = userImage;
        this.userHomepage = userHomepage;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;

    }

    /**
     *
     * @return tracking
     */
    public String getTracking() {
        return tracking;
    }

    /**
     * setter for tracking
     * @param tracking
     */
    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets user homepage
     * @param userHomepage
     */
    public void setUserHomepage(String userHomepage) {
        this.userHomepage = userHomepage;
    }
    /**
     *
     * @return userHomepage
     */
    public String getUserHomepage() {
        return userHomepage;
    }

    /**
     * sets user email
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    /**
     *
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * sets user phone number
     * @param userPhoneNumber
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
    /**
     *
     * @return userPhoneNumber
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     * sets user profile image
     * @param userProfileImage
     */
    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
    /**
     *
     * @return userProfileImage
     */
    public String getUserProfileImage() {
        return userProfileImage;
    }

    /**
     * sets user's saved events
     * @param savedEvents
     */
    public void setSavedEvents(ArrayList<Event> savedEvents) {
        this.savedEvents = savedEvents;
    }
    /**
     *
     * @return savedEvents
     */
    public ArrayList<Event> getSavedEvents() {
        return savedEvents;
    }

    /**
     * sets user's created events
     * @param createdEvents
     */
    public void setCreatedEvents(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    /**
     *
     * @return createdEvents
     */
    public ArrayList<Event> getCreatedEvents() {
        return createdEvents;
    }

    /**
     * sets users old qr list
     * @param oldQRList
     */
    public void setOldQRList(ArrayList<Event> oldQRList) {
        this.oldQRList = oldQRList;
    }
    /**
     *
     * @return oldQRList
     */
    public ArrayList<Event> getOldQRList() {
        return oldQRList;
    }
    /**
     *
     * @return DeviceId
     */
    public String getDeviceId() {
        return DeviceId;
    }
    /**
     *
     * @return lastsaved
     */
    public int getLastsaved() {
        return lastsaved;
    }

    /**
     * sets user's last saved
     * @param lastsaved
     */
    public void setLastsaved(int lastsaved) {
        this.lastsaved = lastsaved;
    }
    /**
     *
     * @return CheckInCount
     */
    public String getCheckInCount() {
        return CheckInCount;
    }

    /**
     * sets user's check-in count
     * @param checkInCount
     */
    public void setCheckInCount(String checkInCount) {
        CheckInCount = checkInCount;
    }
}
