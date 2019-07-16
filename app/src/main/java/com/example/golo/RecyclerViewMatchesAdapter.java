package com.example.golo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Match.Match;

import java.util.ArrayList;

public class RecyclerViewMatchesAdapter extends RecyclerView.Adapter<RecyclerViewMatchesAdapter.ViewHolder> {

    private ArrayList<Match> mData;
    private LayoutInflater mInflater;
    private RecyclerViewMatchesAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecyclerViewMatchesAdapter(Context context, ArrayList<Match> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public RecyclerViewMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.teammatches_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final RecyclerViewMatchesAdapter.ViewHolder holder, final int position) {
        holder.matchDate.setText("Match Date: " + mData.get(position).getUtcDate().substring(0,10));
        holder.matchDate.setTypeface(null, Typeface.BOLD);

        holder.matchDay.setText("Matchday: " + mData.get(position).getMatchday());
        holder.matchDay.setTypeface(null, Typeface.BOLD);

        holder.matchHomeTeam.setText(mData.get(position).getHomeTeam().getName());
        holder.matchAwayTeam.setText(mData.get(position).getAwayTeam().getName());

        if(mData.get(position).getScore().getWinner() != null) {
            holder.matchHomeTeamScore.setText(" - " + mData.get(position).getScore().getFullTime().getHomeTeam());
            holder.matchAwayTeamScore.setText(" - " + mData.get(position).getScore().getFullTime().getAwayTeam());
            if (mData.get(position).getScore().getWinner().equals("HOME_TEAM"))
                holder.matchHomeTeam.setTypeface(null, Typeface.BOLD);
            else if (mData.get(position).getScore().getWinner().equals("AWAY_TEAM")){
                holder.matchAwayTeam.setTypeface(null, Typeface.BOLD);
            }
        } else{
            holder.matchHomeTeamScore.setText(" vs. ");
        }


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MatchActivity.class);
                intent.putExtra("matchId", mData.get(position).getId());
                mInflater.getContext().startActivity(intent);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView matchDate;
        private TextView matchDay;
        private TextView matchHomeTeam;
        private TextView matchAwayTeam;
        private TextView matchAwayTeamScore;
        private TextView matchHomeTeamScore;
        private RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.ll);
            matchDate = itemView.findViewById(R.id.matchDateId);
            matchDay = itemView.findViewById(R.id.matchdayId);
            matchHomeTeam = itemView.findViewById(R.id.matchHomeTeamId);
            matchHomeTeamScore = itemView.findViewById(R.id.matchHomeTeamScoreId);
            matchAwayTeamScore = itemView.findViewById(R.id.matchAwayTeamScoreId);
            matchAwayTeam = itemView.findViewById(R.id.matchAwayTeamId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    Match getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewMatchesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
