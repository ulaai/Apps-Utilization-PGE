package com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uli2.userprofilemgmt.ApplicationActivity;
import com.example.uli2.userprofilemgmt.R;

import java.util.List;

/**
 * Created by user on 7/24/2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public String chart, test;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ApplicationActivity.class);
                    v.getContext().startActivity(intent);

                }
            });

        }

    }
    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        //get chartactivity from album
        final String c = album.getChart();

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new Intent(v.getContext(), Class.forName("com.example.uli2.userprofilemgmt" +
                    c));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Bundle bundle = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) v
                            .getContext())
                            .toBundle();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                v.getContext().startActivity(intent, bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @SuppressWarnings("unchecked") void transitionTo(Intent intent) {


    }
}
