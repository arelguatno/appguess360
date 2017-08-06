package com.example.firedroid.firedroid;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firedroid.firedroid.java_objects.Questions;
import com.example.firedroid.firedroid.java_objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChooseYourLevel extends BaseActivity {

    private DatabaseReference mDatabase;
    private String className = "ChooseYourLevel.";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_choose_your_level);
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    refreshUserProfile(user);
//                } else {
//                    // User is signed out.
//                    refreshUserProfile(user);
//                }
//
//            }
//        };
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    public void firebaseCat(View v) {
        downloadQuestions("BeginnersQuestion", 1);
    }


    private void downloadQuestions(String categroy, final int categoryType) {
        final ArrayList<Questions> questions = new ArrayList<>();
        questions.clear();
        showProgressDialog();

        mDatabase.child(categroy).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(className + "Count ", "" + dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Questions r = postSnapshot.getValue(Questions.class);
                    questions.add(r);
                }

                // Launch GamePlatform Activity
                launchGamePlatformActivity(questions, categoryType);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("arel","Read failed: " + databaseError.getMessage());

            }
        });
    }
    private void launchGamePlatformActivity(ArrayList<Questions> r, int categoryType) {
        if (Constants.categoryType.EASY.getValue() == categoryType) {
            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            this.startActivity(intent);
        } else if (Constants.categoryType.INTERMEDIATE.getValue() == categoryType) {
            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            this.startActivity(intent);
        } else if (Constants.categoryType.HARD.getValue() == categoryType) {
            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            this.startActivity(intent);
        } else {

        }
    }
}
