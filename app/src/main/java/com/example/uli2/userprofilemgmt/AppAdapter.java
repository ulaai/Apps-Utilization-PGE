package com.example.uli2.userprofilemgmt;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 7/19/2017.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.apptitle);
            txtFooter = (TextView) v.findViewById(R.id.appdesc);
        }
    }
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public AppAdapter(List<String> myDataset) {
        values = myDataset;
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
        final String name = values.get(position);

        if(position == 0) {
            holder.layout.setBackgroundColor(Color.BLUE);
        }
        holder.txtHeader.setText(name);
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(position);
            }
        });
        holder.txtFooter.setText("Footer: " + name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
