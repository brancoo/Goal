package com.example.golo;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Player.Player;

import java.util.List;

public class RecyclerViewSquadAdapter extends RecyclerView.Adapter<RecyclerViewSquadAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private RecyclerViewSquadAdapter.ItemClickListener mClickListener;
    private List<Player> teamSquad;

    public RecyclerViewSquadAdapter(Context context, List<Player> teamSquad) {
        this.mInflater = LayoutInflater.from(context);
        this.teamSquad = teamSquad;
    }

    @Override
    public RecyclerViewSquadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.teamsquad_row, parent, false);
        return new RecyclerViewSquadAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewSquadAdapter.ViewHolder holder, int position) {
        holder.squadPlayerName.setText(teamSquad.get(position).getName());
        holder.squadPlayerPosition.setText(teamSquad.get(position).getPosition());
        holder.squadPlayerNationality.setText(teamSquad.get(position).getNationality());
        holder.squadPlayerRole.setText(teamSquad.get(position).getRole());

        holder.squadPlayerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mInflater.getContext(),"PLAYER", Toast.LENGTH_LONG).show();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return teamSquad.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView squadPlayerName;
        private TextView squadPlayerPosition;
        private TextView squadPlayerNationality;
        private TextView squadPlayerRole;

        ViewHolder(View itemView) {
            super(itemView);
            squadPlayerName = itemView.findViewById(R.id.squadPlayerNameId);
            squadPlayerPosition = itemView.findViewById(R.id.squadPlayerPositionId);
            squadPlayerNationality = itemView.findViewById(R.id.squadPlayerNationalityId);
            squadPlayerRole = itemView.findViewById(R.id.squadPlayerRoleId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    Player getItem(int id) {
        return teamSquad.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewSquadAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
