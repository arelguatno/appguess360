package com.example.firedroid.firedroid;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firedroid.firedroid.java_objects.Questions;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_level);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

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
                System.out.println("Read failed: " + databaseError.getMessage());

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
