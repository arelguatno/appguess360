package com.example.firedroid.firedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firedroid.firedroid.java_objects.ReadQuestions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChooseYourLevel extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String className = "ChooseYourLevel.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_level);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void firebaseCat(View v){
      downloadQuestions("BeginnersQuestion");
    }

    private void downloadQuestions(String categroy){
        final List<ReadQuestions> questions = new ArrayList<>();
        questions.clear();

        mDatabase.child(categroy).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(className+"Count " ,""+dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ReadQuestions  r = postSnapshot.getValue(ReadQuestions.class);
                    questions.add(r);
                }

                // Launch GamePlatform Activity
                launchGamePlatformActivity(questions);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Read failed: " + databaseError.getMessage());

            }
        });


    }

    private void launchGamePlatformActivity(List<ReadQuestions> r){

    }
}
