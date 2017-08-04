package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.uli2.userprofilemgmt.R;

/**
 * Created by uli on 04/08/17.
 */

public class VParentViewHolder extends ParentViewHolder {
    public static TextView txtTitle;
    public static TextView txtUtil;

    public TextView imageBtn;
    public View layout;


    public VParentViewHolder(View itemView) {
        super(itemView);
        layout = itemView;
        txtTitle = (TextView)itemView.findViewById(R.id.apptitle);
        txtUtil = (TextView) itemView.findViewById(R.id.app_util);

        imageBtn = (TextView) itemView.findViewById(R.id.show_info);
    }
}
