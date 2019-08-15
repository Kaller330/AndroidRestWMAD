package com.example.h024470h.wmadassignment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;


import DTO.showingDTO;


class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<showingDTO> mDataset;

    public MainAdapter(ArrayList<showingDTO> dataset) {
        mDataset = dataset;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.mName.setText(mDataset.get(position).getShowingID() + "\n" +
                mDataset.get(position).getFilm().getTitle() + "\n" +
                mDataset.get(position).getScreen().getScreenID() + "\n" +
                mDataset.get(position).getTimeslot() + "\n");
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;


        public ViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name);

        }
    }
}