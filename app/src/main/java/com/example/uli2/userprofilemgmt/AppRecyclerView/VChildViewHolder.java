package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.uli2.userprofilemgmt.R;

/**
 * Created by uli on 04/08/17.
 */

public class VChildViewHolder extends ChildViewHolder {
    public static TextView util2;
    public static TextView actual2;
    public static TextView registered2;

    public VChildViewHolder(View v) {
        super(v);
        util2 = (TextView) itemView.findViewById(R.id.util2);
        actual2 = (TextView) itemView.findViewById(R.id.actual2);
        registered2 = (TextView) itemView.findViewById(R.id.register2);
    }
}