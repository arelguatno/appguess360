package com.example.firedroid.firedroid.utility;

/**
 * Created by Arel on 8/5/2017.
 */

public class Constants {
    public static final String DB_NODE_USERS_PROFILE = "usersprofile";
    public static final String DB_ANSWERED_QUESTION= "answered_question";


    public static final String DB_NODE_EASY = "list_of_questions/firebase_category";
    public static final String DB_NODE_ANDROID = "list_of_questions/firebase_android_category";
    public static final String DB_NODE_WEB = "list_of_questions/firebase_web_category";


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


