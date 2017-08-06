package com.example.firedroid.firedroid;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.firedroid.firedroid.java_objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aguatno on 8/6/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected static String playerName;
    protected static String currentLevel;
    protected static String userUid;
    protected static int userStars;
    protected static int currentIndexQuestion;
    DatabaseReference mFirebaseRef;

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

    protected void refreshUserProfile(){
        mFirebaseRef.child("userprofile").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Load Users
                    User r = snapshot.getValue(User.class);
                    User logInUser = new User(r.getCurrentLevel(), playerName, this.getEmail(), r.getStars());
                    setCurrentLevel(r.getCurrentLevel());
                    setUserStars(r.getStars());
                    setUserUid(user.getUid());

                    mFirebaseRef.child(Constants.DB_NODE_USER_PROFILE).child(user.getUid()).setValue(logInUser);
                } else {
                    // Create User Profile
                    User logInUser = new User("new_player", playerName, user.getEmail(), 0);
                    setCurrentLevel("new_player");
                    setUserStars(0);
                    setUserUid(user.getUid());
                    mFirebaseRef.child(Constants.DB_NODE_USER_PROFILE).child(user.getUid()).setValue(logInUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}