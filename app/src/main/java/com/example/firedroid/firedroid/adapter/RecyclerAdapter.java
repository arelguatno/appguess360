package com.example.firedroid.firedroid.adapter;

/**
 * Created by aguatno on 8/9/17.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firedroid.firedroid.ChooseYourLevel;
import com.example.firedroid.firedroid.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ChooseYourLevel r;

    private String[] titles = {"Firebase product",
            "Android",
            "Web"};

    private String[] details = {"Difficulty: Easy",
            "Difficulty: Hard", "Difficulty: Hard"};

    private int[] images = { R.drawable.firebase_img,
            R.drawable.android,
            R.drawable.javascript};

    private String[] requiredStars= {"Click here to play",
            "Required stars to play :10 ", "Required stars to play :10"};

    public RecyclerAdapter(ChooseYourLevel chooseYourLevel) {
        this.r = chooseYourLevel;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView itemRequiredStars;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            itemRequiredStars = (TextView)itemView.findViewById(R.id.item_required_stars);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    r.onItemClickListener(position);

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        viewHolder.itemRequiredStars.setText(requiredStars[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}