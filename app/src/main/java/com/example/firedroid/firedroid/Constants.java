package com.example.firedroid.firedroid;

/**
 * Created by Arel on 8/5/2017.
 */

public class Constants {

    public enum categoryType {
        EASY(1), INTERMEDIATE(2), HARD(3);

        private final int value;

        private categoryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum typeOfQuestion {
        TEXT, PICTURE
    }
}


