package com.example.firedroid.firedroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firedroid.firedroid.adapter.RecyclerAdapter;
import com.example.firedroid.firedroid.java_objects.Questions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.firedroid.firedroid.Constants.DB_NODE_EASY;
import static com.example.firedroid.firedroid.Constants.DB_NODE_LEGEND;
import static com.example.firedroid.firedroid.Constants.DB_NODE_MASTER;

public class ChooseYourLevel extends BaseActivity {

    private DatabaseReference mDatabase;
    private String className = "ChooseYourLevel.";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button cat_easy;
    ArrayList<String> singleAddress = new ArrayList<String>();
    long totalQuestion=0;
    long totalAnsweredQuestion=0;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_choose_your_level);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cat_easy = (Button) findViewById(R.id.cat_easy);

        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(ChooseYourLevel.this);
        recyclerView.setAdapter(adapter);
    }

    public void onItemClickListener(){
        downloadQuestions(DB_NODE_EASY, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Count Easy answered question

        mDatabase.child(Constants.DB_ANSWERED_QUESTION).child(getUserUid()).child(String.valueOf(Constants.categoryType.EASY.getValue())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cat_easy.setText("");
                singleAddress.clear();
                Log.d(className + "Count ", "" + dataSnapshot.getChildrenCount());
                totalAnsweredQuestion = dataSnapshot.getChildrenCount();
                cat_easy.setText("EASY ( " + String.valueOf(dataSnapshot.getChildrenCount()) + " / ");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    singleAddress.add((String) postSnapshot.child("qid").getValue());
                }
                // Count Easy questions
                mDatabase.child(Constants.DB_NODE_EASY).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(className + "Easy Question total: ", "" + dataSnapshot.getChildrenCount());
                        totalQuestion = dataSnapshot.getChildrenCount();
                        cat_easy.setText(cat_easy.getText() + String.valueOf(dataSnapshot.getChildrenCount()) + " ) ");
                        if(totalQuestion == totalAnsweredQuestion){
                            cat_easy.setEnabled(false);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("arel", "Read failed: " + databaseError.getMessage());

            }
        });


    }

    private void downloadQuestions(String category, final int categoryType) {
        final ArrayList<Questions> questions = new ArrayList<>();
        questions.clear();
        showProgressDialog("Please wait...");

        mDatabase.child(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(className + "Count ", "" + dataSnapshot.getChildrenCount());

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
                Log.d("arel", "Read failed: " + databaseError.getMessage());

            }
        });
    }

    private void launchGamePlatformActivity(ArrayList<Questions> r, int categoryType) {

        if (Constants.categoryType.EASY.getValue() == categoryType) {

            for (int x = 0; x < singleAddress.size(); x++) {
                Log.d("arel", singleAddress.get(x));

                for(int y =0; y < r.size(); y++){
                    if(singleAddress.get(x).toString().equals(r.get(y).getId())){
                        r.remove(y);
                    }
                }
            }
            Log.d("arel", String.valueOf(r.size()));
            Intent intent = new Intent(this, GamePlatform.class);
            Log.d("totalquestions", String.valueOf(r.size()));
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);


        } else if (Constants.categoryType.INTERMEDIATE.getValue() == categoryType) {

            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);

        } else if (Constants.categoryType.HARD.getValue() == categoryType) {

            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);

        } else {

        }
    }
}
