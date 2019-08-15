package com.example.h024470h.wmadassignment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;


import DTO.bookingDTO;
import DTO.showingDTO;


class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.ViewHolder> {

    ArrayList<bookingDTO> mDataset;

    public bookingAdapter(ArrayList<bookingDTO> dataset) {
        mDataset = dataset;
    }

    @Override
    public bookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bookingAdapter.ViewHolder holder, int position) {
        holder.mName.setText(mDataset.get(position).getBookingID() + "\n" +
                mDataset.get(position).getShowing().getFilm().getTitle()+ "\n" +
                mDataset.get(position).getScreen().getScreenID() + "\n" +
                mDataset.get(position).getScreen().getCinema().getName()+ "\n");
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