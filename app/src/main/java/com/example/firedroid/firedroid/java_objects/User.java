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

    public User() {
    }

    public User(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.lastUpdate = timestampNowObject;
    }

    public User(String currentLevel, String name, String emailAddress) {
        this.currentLevel = currentLevel;
        this.name = name;
        this.emailAddress = emailAddress;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this.lastUpdate = timestampNowObject;
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

    @Exclude
    public long getTimestampCreatedLong() {
        return (long) lastUpdate.get("timestamp");
    }
}
