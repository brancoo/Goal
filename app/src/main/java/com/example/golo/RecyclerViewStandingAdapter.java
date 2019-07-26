package com.example.golo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Standing.StandingTeam;

import java.util.List;

public class RecyclerViewStandingAdapter extends RecyclerView.Adapter<RecyclerViewStandingAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private RecyclerViewStandingAdapter.ItemClickListener mClickListener;
    private final List<StandingTeam> standingTeams;
    private final String compId;

    public RecyclerViewStandingAdapter(Context context, List<StandingTeam> standingTeams, String compId) {
        this.mInflater = LayoutInflater.from(context);
        this.standingTeams = standingTeams;
        this.compId = compId;
    }

    @Override
    public RecyclerViewStandingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.standingrow, parent, false);
        return new RecyclerViewStandingAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final RecyclerViewStandingAdapter.ViewHolder holder, int position) {
        holder.textViewTeamPosition.setText(standingTeams.get(position).getPosition() + ".");
        holder.textViewTeamName.setText(standingTeams.get(position).getTeam().getName());
        holder.textViewTeamPoints.setText(standingTeams.get(position).getPoints());
        holder.textViewTeamWins.setText(standingTeams.get(position).getWon());
        holder.textViewTeamDraws.setText(standingTeams.get(position).getDraw());
        holder.textViewTeamLosses.setText(standingTeams.get(position).getLost());
        holder.textViewTeamGoalsScored.setText(standingTeams.get(position).getGoalsFor());
        holder.textViewTeamGoalsAgainst.setText(standingTeams.get(position).getGoalsAgainst());
        holder.textViewTeamGoalsDifference.setText(standingTeams.get(position).getGoalDifference());

        holder.textViewTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamActivity.class);
                intent.putExtra("teamId", standingTeams.get(holder.getAdapterPosition()).getTeam().getId());
                intent.putExtra("teamName", standingTeams.get(holder.getAdapterPosition()).getTeam().getName());
                intent.putExtra("compId", compId);
                mInflater.getContext().startActivity(intent);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return standingTeams.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textViewTeamPosition;
    public final TextView textViewTeamName;
    public final TextView textViewTeamPoints;
    public final TextView textViewTeamWins;
    public final TextView textViewTeamDraws;
    public final TextView textViewTeamLosses;
    public final TextView textViewTeamGoalsScored;
    public final TextView textViewTeamGoalsAgainst;
    public final TextView textViewTeamGoalsDifference;

    ViewHolder(View itemView) {
        super(itemView);
        textViewTeamPosition = itemView.findViewById(R.id.standingTeamPosition);
        textViewTeamName = itemView.findViewById(R.id.standingTeamNameId);
        textViewTeamPoints = itemView.findViewById(R.id.standingTeamPoints);
        textViewTeamWins = itemView.findViewById(R.id.standingTeamWins);
        textViewTeamDraws = itemView.findViewById(R.id.standingTeamDraws);
        textViewTeamLosses = itemView.findViewById(R.id.standingTeamLosses);
        textViewTeamGoalsScored = itemView.findViewById(R.id.standingTeamGoalsScored);
        textViewTeamGoalsAgainst = itemView.findViewById(R.id.standingTeamGoalsAgainst);
        textViewTeamGoalsDifference = itemView.findViewById(R.id.standingTeamGoalsDifference);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null)
            mClickListener.onItemClick(view, getAdapterPosition());
    }
}

    // convenience method for getting data at click position
    String getItem(int id) {
        return standingTeams.get(id).getTeam().getId();
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewStandingAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
