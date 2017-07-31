package com.example.uli2.userprofilemgmt;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/19/2017.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<String> label;
    private List<String> util_score;
    private List<List<String>> myDataset;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView txtUtil;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.apptitle);
            txtFooter = (TextView) v.findViewById(R.id.appdesc);
            txtUtil = (TextView) v.findViewById(R.id.app_util);
        }
    }
    public void add(int position, String item) {
        label.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        label.remove(position);
        notifyItemRemoved(position);
    }

    public AppAdapter(List<List<String>> myDataset) {
        this.myDataset = myDataset;
        label = myDataset.get(0);
        util_score = myDataset.get(3);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.app_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //get element from your dataset at this position and replace the contents of the view
        // with the element

        final String name = label.get(position);
        final String utilscore = util_score.get(position);

        if(position == 0) {
            holder.layout.setBackgroundResource(R.color.top1app);
        } else if (position == 1) {
            holder.layout.setBackgroundResource(R.color.top2app);
        } else if (position == 2) {
            holder.layout.setBackgroundResource(R.color.top3app);
        }else if (position == label.size()-3) {
            holder.layout.setBackgroundResource(R.color.bottom3app);
        }else if (position == label.size()-2) {
            holder.layout.setBackgroundResource(R.color.bottom2app);
        } else if (position == label.size()-1) {
            holder.layout.setBackgroundResource(R.color.bottom1app);
        } else {
            holder.layout.setBackgroundColor(Color.WHITE);
        }
        holder.txtHeader.setText(name);
//        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                remove(position);
//            }
//        });
        holder.txtUtil.setText(utilscore);
        holder.txtFooter.setText("Footer: " + name);
    }

    @Override
    public int getItemCount() {
        return label.size();
    }
}
