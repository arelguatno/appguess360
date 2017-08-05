package com.example.firedroid.firedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firedroid.firedroid.java_objects.ReadQuestions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GamePlatform extends AppCompatActivity {
    static ArrayList<ReadQuestions> listOfQuestions;
    private int listOfTextViews[] = {R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5,R.id.textView6,R.id.textView7,R.id.textView8,R.id.textView9,R.id.textView10,R.id.textView11,R.id.textView12,R.id.textView13,R.id.textView14,R.id.textView15,R.id.textView16};
    private int listOfButtons[] = {R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12,};
    private int listOfImages[] = {R.id.image1,R.id.image2,R.id.image3,R.id.image4};
    private String className = "GamePlatform.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_platform);

        listOfQuestions = (ArrayList<ReadQuestions>) getIntent().getSerializableExtra("listOfQuestions");
        populateQuestion();
    }

    private void populateQuestion() {
        clearFields();

        int randNum = (new Random()).nextInt(listOfQuestions.size());
        Log.d("arel",listOfQuestions.get(randNum).getTypeofquestion());
        StringBuilder sb = new StringBuilder();

        if(listOfQuestions.get(randNum).getTypeofquestion().equals("PICTURES")){

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

            String str = listOfQuestions.get(randNum).getAnswer();
            char arr[] = str.toCharArray();

            for(char letter: arr){   // Loop through each character

                Random r = new Random();
                Set<Integer> uniqueNumbers = new HashSet<>();
                while (uniqueNumbers.size()< str.length()){
                    uniqueNumbers.add(r.nextInt(listOfButtons.length));
                }
                for (Integer i : uniqueNumbers){
                    Button btn = (Button) findViewById(listOfButtons[i]);
                    btn.setText(String.valueOf(letter));
                }
            }

        }else{

        }
    }

    private void clearFields(){
        for(int i=0; i< listOfTextViews.length; i++){
            TextView txt = (TextView) findViewById(listOfTextViews[i]);
            txt.setText("");
        }

        for(int i=0; i< listOfButtons.length; i++){
            Button btn = (Button) findViewById(listOfButtons[i]);
            btn.setText("");
        }

        for(int i=0; i< listOfImages.length; i++){
            ImageView img = (ImageView) findViewById(listOfImages[i]);
            img.setImageResource(0);
        }

    }

    private void loadQuestions(){

    }
}
