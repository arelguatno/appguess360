package com.example.firedroid.firedroid;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by aguatno on 8/6/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected static String playerName;
    protected static String currentLevel;
    protected static String userUid;
    protected static int userStars;
    protected static int currentIndexQuestion;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
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

    protected void setToFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}