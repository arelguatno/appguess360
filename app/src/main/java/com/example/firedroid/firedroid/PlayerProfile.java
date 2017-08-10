package com.example.firedroid.firedroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/**
 * Created by yescalona on 8/9/17.
 */

public class PlayerProfile extends BaseActivity {

    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_profile);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        ImageView img_player = (ImageView)findViewById(R.id.playerPicture);
        Glide.with(PlayerProfile.this)
                .load(getPhotoUrl())
                .into(img_player);

        TextView textView_name = (TextView)findViewById(R.id.playerName);
        textView_name.setText(playerName);
        Log.d(TAG, "Name:" + playerName);

        TextView textView_rank = (TextView)findViewById(R.id.playerRank);
        textView_rank.setText(playerRank);
        Log.d(TAG, "Rank:" + playerRank);


        String stars = Integer.toString(userStars);
        TextView textView_stars = (TextView)findViewById(R.id.currentStars);
        textView_stars.setText(stars);
        Log.d(TAG, "Rank:" + stars);




    }

}
