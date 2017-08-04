package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.uli2.userprofilemgmt.R;

/**
 * Created by uli on 04/08/17.
 */

public class VChildViewHolder extends ChildViewHolder {
    public static TextView option1;
    public static TextView option2;

    public VChildViewHolder(View v) {
        super(v);
        option1 = (TextView) itemView.findViewById(R.id.option1);
        option2 = (TextView) itemView.findViewById(R.id.option2);
    }
}