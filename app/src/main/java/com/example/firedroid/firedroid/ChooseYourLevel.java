package com.example.firedroid.firedroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.firedroid.firedroid.adapter.RecyclerAdapter;
import com.example.firedroid.firedroid.java_objects.Questions;
import com.example.firedroid.firedroid.utility.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.firedroid.firedroid.utility.Constants.DB_NODE_ANDROID;
import static com.example.firedroid.firedroid.utility.Constants.DB_NODE_EASY;
import static com.example.firedroid.firedroid.utility.Constants.DB_NODE_WEB;

public class ChooseYourLevel extends BaseActivity {

    ArrayList<String> getTotalAnsweredQuestion = new ArrayList<String>();
    long totalQuestion = 0;
    long totalAnsweredQuestion = 0;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private String className = "ChooseYourLevel.";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button cat_easy;

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

    public void onItemClickListener(int position) {
        if (position == 0) { // Firebase feature
            setSelectedCategory(Constants.categoryType.firebase.toString());
            setQuestions_node(DB_NODE_EASY);
        } else if (position == 1) { // Android
            setSelectedCategory(Constants.categoryType.android.toString());
            setQuestions_node(DB_NODE_ANDROID);
        } else if (position == 2) { // WEB
            setSelectedCategory(Constants.categoryType.web.toString());
            setQuestions_node(DB_NODE_WEB);
        }

        mDatabase.child(Constants.DB_ANSWERED_QUESTION).child(getUserUid()).child(getSelectedCategory()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getTotalAnsweredQuestion.clear();
                Log.d(className + "Count ", "" + dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    getTotalAnsweredQuestion.add((String) postSnapshot.child("qid").getValue());
                }

                downloadQuestions(getQuestions_node(), getSelectedCategory());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("arel", "Read failed: " + databaseError.getMessage());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void downloadQuestions(String category, final String categoryType) {
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

    private void launchGamePlatformActivity(ArrayList<Questions> r, String categoryType) {

        for (int x = 0; x < getTotalAnsweredQuestion.size(); x++) {
            Log.d("arel", getTotalAnsweredQuestion.get(x));

            for (int y = 0; y < r.size(); y++) {
                if (getTotalAnsweredQuestion.get(x).toString().equals(r.get(y).getId())) {
                    r.remove(y);
                }
            }
        }

        if (Constants.categoryType.firebase.toString().equalsIgnoreCase(categoryType) && r.size() != 0) {

            Intent intent = new Intent(this, GamePlatform.class);
            Log.d("totalquestions", String.valueOf(r.size()));
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);


        } else if (Constants.categoryType.android.toString().equalsIgnoreCase(categoryType) && r.size() != 0) {

            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);


        } else if (Constants.categoryType.web.toString().equalsIgnoreCase(categoryType) && r.size() != 0) {

            Intent intent = new Intent(this, GamePlatform.class);
            intent.putExtra("listOfQuestions", r);
            intent.putExtra("category", categoryType);
            this.startActivity(intent);

        } else {
            Toast.makeText(this, "You already finished this category", Toast.LENGTH_SHORT).show();

        }
    }
}
