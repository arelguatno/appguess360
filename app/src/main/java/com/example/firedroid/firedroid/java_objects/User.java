package com.example.firedroid.firedroid.java_objects;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by aguatno on 8/6/17.
 */
@IgnoreExtraProperties
public class User {

    private String currentLevel;
    private String name;
    private String emailAddress;
    private HashMap<String, Object> lastUpdate;
    private int stars;

    public User() {
    }

    public User(String currentLevel, String name, String emailAddress, int stars) {
        this.currentLevel = currentLevel;
        this.name = name;
        this.emailAddress = emailAddress;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.lastUpdate = timestampNowObject;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public HashMap<String, Object> getLastUpdate()  {
        return lastUpdate;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Exclude
    public long getTimestampCreatedLong() {
        return (long) lastUpdate.get("timestamp");
    }
}
