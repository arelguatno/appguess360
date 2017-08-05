package com.example.firedroid.firedroid.java_objects;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Arel on 8/5/2017.
 */

@IgnoreExtraProperties
public class ReadQuestions implements Serializable{
    public String answer;
    public String image1;
    public String image2;
    public String image3;
    public String image4;
    public String qid;
    public String question;
    public String typeofquestion;

    public ReadQuestions() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ReadQuestions(String answer, String id, String image1, String image2, String image3, String image4, String question, String typeofquestion) {
        this.answer = answer;
        this.qid = id;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.question = question;
        this.typeofquestion = typeofquestion;
    }

    public String getId() {
        return qid;
    }

    public void setId(String id) {
        this.qid = id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getTypeofquestion() {
        return typeofquestion;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTypeofquestion(String typeofquestion) {
        this.typeofquestion = typeofquestion;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }
}
