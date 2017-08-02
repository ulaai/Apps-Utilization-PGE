package com.example.uli2.userprofilemgmt;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by user on 7/19/2017.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<String> label;
    private List<String> util_score;
    private List<String> num_actual;
    private boolean isExpanded = false;

    private List<List<String>> myDataset;
    private HashSet<Integer> expandedPositionSet;
    private ListExpandListener listExpandListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtHeader;
        public TextView txtUtil;
        public TextView txtActual;
        private TextView showInfo;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.apptitle);
            txtUtil = (TextView) v.findViewById(R.id.app_util);
//            txtActual = (TextView) v.findViewById(R.id.app_actual);
            showInfo = (TextView) v.findViewById(R.id.show_info);

            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

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
        num_actual = myDataset.get(2);
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
        final String numactual = num_actual.get(position);


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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    listExpandListener.onCollapse();
                    isExpanded = false;
                } else {
                    listExpandListener.onExpand();
                    isExpanded = true;
                }
            }
        });
//        holder.txtActual.setText(numactual);

    }

    @Override
    public int getItemCount() {
        return label.size();
    }

    public void setListExpandListener(ListExpandListener listItemListener) {
        this.listExpandListener = listItemListener;
    }

}
