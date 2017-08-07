package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.uli2.userprofilemgmt.R;

import java.util.List;

/**
 * Created by uli on 04/08/17.
 */

public class CAdapter extends ExpandableRecyclerAdapter <VParentViewHolder, VChildViewHolder> {
        LayoutInflater inflater;

public CAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        }

@Override
public VParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.app_item_layout,viewGroup,false);
        return new VParentViewHolder(view);
        }

@Override
public VChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.app_item_child_layout,viewGroup,false);
        return new VChildViewHolder(view);
        }

@Override
public void onBindParentViewHolder(VParentViewHolder holder, int position, Object o) {
        AppTitleParent title = (AppTitleParent) o;
        VParentViewHolder.txtTitle.setText(title.getTitle());
        VParentViewHolder.txtUtil.setText(title.getUtil());

        int labelSize = AppTitles.numLabel;

        if(position == 0) {
            holder.layout.setBackgroundResource(R.color.top1app);
        } else if (position == 1) {
            holder.layout.setBackgroundResource(R.color.top2app);
        } else if (position == 2) {
            holder.layout.setBackgroundResource(R.color.top3app);
        }else if (position == labelSize-4) {
            holder.layout.setBackgroundResource(R.color.bottom3app);
        }else if (position == labelSize-3) {
            holder.layout.setBackgroundResource(R.color.bottom2app);
        } else if (position == labelSize-2) {
            holder.layout.setBackgroundResource(R.color.bottom1app);
        } else {
            holder.layout.setBackgroundColor(Color.WHITE);
        }


}

@Override
public void onBindChildViewHolder(VChildViewHolder titleChildViewHolder, int i, Object o) {
        AppTitleChild title = (AppTitleChild)o;
        VChildViewHolder.util2.setText(title.getUtil2());
        VChildViewHolder.actual2.setText(title.getActual2());
        VChildViewHolder.registered2.setText(title.getRegister2());


}

}
