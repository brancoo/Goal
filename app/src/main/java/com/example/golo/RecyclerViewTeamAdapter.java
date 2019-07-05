package com.example.golo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Team.Team;

import java.util.List;

public class RecyclerViewTeamAdapter extends RecyclerView.Adapter<RecyclerViewTeamAdapter.ViewHolder> {
    private List<Team> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String stringResource;

    RecyclerViewTeamAdapter(Context context, List<Team> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        stringResource = context.getString(R.string.foundation_year);
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

        holder.teamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamActivity.class);
                intent.putExtra("teamId", mData.get(holder.getAdapterPosition()).getId());
                intent.putExtra("teamName", mData.get(holder.getAdapterPosition()).getName());
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
        public TextView teamName;
        public TextView teamFoundation;
        public TextView teamStadium;

        ViewHolder(View itemView) {
            super(itemView);
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

