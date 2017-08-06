package com.example.firedroid.firedroid;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firedroid.firedroid.java_objects.Questions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GamePlatform extends BaseActivity implements View.OnClickListener {
    static ArrayList<Questions> listOfQuestions;
    private int listOfTextViews[] = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8};
    private int listOfButtons[] = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12};
    private int listOfImages[] = {R.id.image1, R.id.image2, R.id.image3, R.id.image4};
    private String alphabets[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z"};
    private String className = "GamePlatform.";
    private String currentLevel = "";
    String correctAnswer;
    private DatabaseReference mDatabase;
    TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_game_platform);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        timer = (TextView) findViewById(R.id.timer);
        listOfQuestions = (ArrayList<Questions>) getIntent().getSerializableExtra("listOfQuestions");
        populateQuestion();
        updateCurrentLevel();

    }

    private void updateCurrentLevel() {
        Log.d("arel",getUserUid());
        Log.d("arel",getCurrentLevel());
        mDatabase.child(Constants.DB_NODE_USER_PROFILE).child(getUserUid()).child("currentLevel").setValue(getCurrentLevel(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("arel","Data could not be saved. " + databaseError.getMessage());
                } else {
                    Log.d("arel","Data saved successfully.");
                }
            }
        });
    }

    private void populateQuestion() {
        clearFields();

        int randNum = 0;
        boolean previousQuestion = false;

        for (int x = 0; x < listOfQuestions.size(); x++) {
            if (listOfQuestions.get(x).getId().toString().equalsIgnoreCase(getCurrentLevel())) {
                randNum = x;
                previousQuestion = true;
            }
        }

        if (previousQuestion) {
            currentLevel = listOfQuestions.get(randNum).getId();
            setCurrentLevel(currentLevel);
        } else {
            // Random Questions
            randNum = (new Random()).nextInt(listOfQuestions.size());
            currentLevel = listOfQuestions.get(randNum).getId();
            setCurrentLevel(currentLevel);
        }

        setCurrentIndexQuestion(randNum);

        if (listOfQuestions.get(randNum).getTypeofquestion().equals("PICTURES")) {

            // Images
            ImageView img1 = (ImageView) findViewById(R.id.image1);
            ImageView img2 = (ImageView) findViewById(R.id.image2);
            ImageView img3 = (ImageView) findViewById(R.id.image3);
            ImageView img4 = (ImageView) findViewById(R.id.image4);

            Glide.with(this).load(listOfQuestions.get(randNum).getImage1()).into(img1);
            Glide.with(this).load(listOfQuestions.get(randNum).getImage2()).into(img2);
            Glide.with(this).load(listOfQuestions.get(randNum).getImage3()).into(img3);
            Glide.with(this).load(listOfQuestions.get(randNum).getImage4()).into(img4);

            // Buttons
            // Shuffle Buttons arrangement
            Integer[] shuffleButtons = new Integer[listOfButtons.length];
            for (int i = 0; i < listOfButtons.length; i++) {
                shuffleButtons[i] = i;
            }
            Collections.shuffle(Arrays.asList(shuffleButtons));

            // Populate correct answer first

            //correctAnswer = removeDuplicateLetters(listOfQuestions.get(randNum).getAnswer());
            correctAnswer = listOfQuestions.get(randNum).getAnswer();
            for (int x = 0; x < correctAnswer.length(); x++) {  // Loop through text
                Button btn = (Button) findViewById(listOfButtons[shuffleButtons[x]]);
                btn.setText(String.valueOf(correctAnswer.charAt(x)));
            }
            // Hide some of answers box
            for (int i = 0; i < listOfTextViews.length; i++) {
                int answerLength = listOfQuestions.get(randNum).getAnswer().length() - 1;
                if (i > answerLength) {
                    TextView txt = (TextView) findViewById(listOfTextViews[i]);
                    txt.setVisibility(View.GONE);
                }
            }


            //Populate buttons without letter
            for (int i = 0; i < listOfButtons.length; i++) {
                Button btn = (Button) findViewById(listOfButtons[i]);
                if (btn.getText().equals("")) {
                    char randomLetter = (char) ('a' + Math.random() * ('z' - 'a' + 1));
                    btn.setText(String.valueOf(randomLetter));
                }
            }

        } else {

        }
        startTimer();
    }

    private void clearFields() {
        for (int i = 0; i < listOfTextViews.length; i++) {
            TextView txt = (TextView) findViewById(listOfTextViews[i]);
            txt.setText("");
            txt.setVisibility(View.VISIBLE);
            txt.setOnClickListener(this);
        }

        for (int i = 0; i < listOfButtons.length; i++) {
            Button btn = (Button) findViewById(listOfButtons[i]);
            btn.setText("");
            btn.setEnabled(true);
            btn.setOnClickListener(this);
        }

        for (int i = 0; i < listOfImages.length; i++) {
            ImageView img = (ImageView) findViewById(listOfImages[i]);
            img.setImageResource(0);
            img.setOnClickListener(this);
        }
    }

    private String removeDuplicateLetters(String string) {
        char[] chars = string.toCharArray();
        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : chars) {
            charSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }

        return sb.toString();
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            for (int i = 0; i < listOfButtons.length; i++) {
                if (view.getId() == listOfButtons[i]) {
                    Button btn = (Button) findViewById(listOfButtons[i]);
                    boolean good = sendTextToAnswerBox((String) btn.getText());

                    btn.setEnabled(good);
                    checkIfAnsweIsCorrect();
                }
            }


        } else if (view instanceof TextView) {
            for (int i = 0; i < listOfTextViews.length; i++) {
                if (view.getId() == listOfTextViews[i]) {

                    TextView txt = (TextView) findViewById(listOfTextViews[i]);

                    for (int ii = 0; ii < listOfButtons.length; ii++) {
                        Button btn = (Button) findViewById(listOfButtons[ii]);
                        if (txt.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                            btn.setEnabled(true);
                        }

                    }
                    txt.setText("");
                }

            }
        }
    }

    private void checkIfAnsweIsCorrect() {
        String playerAnswer = "";

        for (int i = 0; i < listOfTextViews.length; i++) {
            TextView txt = (TextView) findViewById(listOfTextViews[i]);
            if (txt.getVisibility() == View.VISIBLE && !txt.equals("")) {
                playerAnswer = playerAnswer + txt.getText();
            }
        }

        if (playerAnswer.equalsIgnoreCase(correctAnswer)) {
            int newStars = getUserStars() + 3;
            mDatabase.child(Constants.DB_NODE_USER_PROFILE).child(getUserUid()).child("stars").setValue(newStars);
            setUserStars(newStars);
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();

            // Generate new question
            listOfQuestions.remove(getCurrentIndexQuestion());
            setCurrentLevel("next_level");
            populateQuestion();
        } else {
            Log.d("arel", "INCORRECT");
        }
    }

    private boolean sendTextToAnswerBox(String letter) {
        for (int i = 0; i < listOfTextViews.length; i++) {
            TextView txt = (TextView) findViewById(listOfTextViews[i]);
            if (txt.getText().equals("") && txt.getVisibility() == View.VISIBLE) {
                txt.setText(letter.toUpperCase());
                return false;
            }
        }
        return true;
    }
    private void startTimer(){
        new CountDownTimer(41000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Answer within " + millisUntilFinished / 1000 + " seconds and get 3 STAR");
            }

            public void onFinish() {
                new CountDownTimer(81000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("Answer within " + millisUntilFinished / 1000 + " seconds and get 2 STAR");
                    }

                    public void onFinish() {
                        timer.setText("This round gives you 1 STAR ");
                    }
                }.start();
            }
        }.start();

    }
}
