package com.example.firedroid.firedroid;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by aguatno on 8/6/17.
 */

public class BaseActivity extends AppCompatActivity {
    static String playerName;
    static String currentLevel;
    static String userUid;
    static int userStars;  // Profile stars
    static int currentIndexQuestion;
    static int starScore;  // level stars
    static Uri photoUrl;
    static String selectedCategory;
    static String questions_node;
    static String playerRank;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public static String getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(String currentLevel) {
        BaseActivity.currentLevel = currentLevel;
    }

    public static String getUserUid() {
        return userUid;
    }

    public static void setUserUid(String userUid) {
        BaseActivity.userUid = userUid;
    }

    public static int getUserStars() {
        return userStars;
    }

    public static void setUserStars(int userStars) {
        BaseActivity.userStars = userStars;
    }

    public static int getCurrentIndexQuestion() {
        return currentIndexQuestion;
    }

    public static void setCurrentIndexQuestion(int currentIndexQuestion) {
        BaseActivity.currentIndexQuestion = currentIndexQuestion;
    }

    public static void setStarScore(int starScore) {
        BaseActivity.starScore = starScore;
    }

    public static int getStarScore() {
        return starScore;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    protected void setToFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static String getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(String selectedCategory) {
        BaseActivity.selectedCategory = selectedCategory;
    }

    public static String getQuestions_node() {
        return questions_node;
    }

    public static void setQuestions_node(String questions_node) {
        BaseActivity.questions_node = questions_node;
    }

    public static String getPlayerRank() {
        return playerRank;
    }

    public static void setPlayerRank(String playerRank) {
        BaseActivity.playerRank = playerRank;
    }

}