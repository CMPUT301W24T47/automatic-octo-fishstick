package com.example.eventapptest2;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a User
 */
public class User implements Serializable {
    String userName;
    String userHomepage;
    String userEmail;
    String userPhoneNumber;
    String userProfileImage;
    ArrayList<Event> savedEvents;
    ArrayList<Event> createdEvents;
    ArrayList<Event> oldQRList;



    public User(String userName, String userHomepage, String userEmail, String userPhoneNumber, String userProfileImage, ArrayList<Event> savedEvents, ArrayList<Event> createdEvents, ArrayList<Event> oldQRList) {
        this.userName = userName;
        this.userHomepage = userHomepage;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userProfileImage = userProfileImage;
        this.savedEvents = savedEvents;
        this.createdEvents = createdEvents;
        this.oldQRList = oldQRList;
    }

    protected User(Parcel in) {
        userName = in.readString();
        userHomepage = in.readString();
        userEmail = in.readString();
        userPhoneNumber = in.readString();
        userProfileImage = in.readString();
    }

//    public static final Creator<User> CREATOR = new Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };

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


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeString(userName);
//        dest.writeString(userHomepage);
//        dest.writeString(userEmail);
//        dest.writeString(userPhoneNumber);
//        dest.writeString(userProfileImage);
//    }
}

