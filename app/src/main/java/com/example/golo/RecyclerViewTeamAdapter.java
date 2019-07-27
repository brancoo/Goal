package com.example.golo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Team.Team;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecyclerViewTeamAdapter extends RecyclerView.Adapter<RecyclerViewTeamAdapter.ViewHolder> {
    private final List<Team> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final String stringResource;
    private final String compId;
    private int index = -1;

    RecyclerViewTeamAdapter(Context context, List<Team> data, String compId) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        stringResource = context.getString(R.string.foundation_year);
        this.compId = compId;
        Collections.sort(mData, new Comparator<Team>() { //sort alphabetically the list of teams!
                    public int compare(Team team1, Team team2)
                    { return team1.getName().compareTo(team2.getName()); }
                });
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerViewTeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.teams_row, parent, false);
        return new RecyclerViewTeamAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final RecyclerViewTeamAdapter.ViewHolder holder, int position) {
        holder.teamName.setText(mData.get(position).getName());
        holder.teamFoundation.setText(stringResource + mData.get(position).getFounded());
        holder.teamStadium.setText(mData.get(position).getVenue());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamActivity.class);
                intent.putExtra("teamId", mData.get(holder.getAdapterPosition()).getId());
                intent.putExtra("teamName", mData.get(holder.getAdapterPosition()).getName());
                intent.putExtra("compId", compId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView teamName;
        private final TextView teamFoundation;
        private final TextView teamStadium;
        private final RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.teams_rowLayoutId);
            teamName = itemView.findViewById(R.id.teamNameId);
            teamFoundation = itemView.findViewById(R.id.teamFoundationId);
            teamStadium = itemView.findViewById(R.id.teamStadiumId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Team getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

