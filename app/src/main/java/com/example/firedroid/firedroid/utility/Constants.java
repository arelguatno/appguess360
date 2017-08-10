package com.example.firedroid.firedroid.utility;

/**
 * Created by Arel on 8/5/2017.
 */

public class Constants {
    public static final String DB_NODE_USERS_PROFILE = "usersprofile";
    public static final String DB_NODE_EASY = "BeginnersQuestion";
    public static final String DB_ANSWERED_QUESTION= "answered_question";

//    public static final String DB_NODE_EASY = "firebase_level_questions";
    public static final String DB_NODE_ANDROID = "android_level_questions";
    public static final String DB_NODE_WEB = "web_level_questions";


//    public enum categoryType {
//        EASY(1), INTERMEDIATE(2), HARD(3);
//
//        private final int value;
//
//        private categoryType(int value) {
//            this.value = value;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }

    public enum typeOfQuestion {
        TEXT, PICTURE
    }

    public enum categoryType {
        firebase,android,ios,web;
    }
}


