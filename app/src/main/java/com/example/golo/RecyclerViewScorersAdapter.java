package com.example.golo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Player.Scorer;

import java.util.List;

public class RecyclerViewScorersAdapter extends RecyclerView.Adapter<RecyclerViewScorersAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private RecyclerViewScorersAdapter.ItemClickListener mClickListener;
    private final List<Scorer> scorerList;

    public RecyclerViewScorersAdapter(Context context, List<Scorer> scorerList) {
        this.mInflater = LayoutInflater.from(context);
        this.scorerList = scorerList;
    }

    @Override
    public RecyclerViewScorersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.scorers_row, parent, false);
        return new RecyclerViewScorersAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerViewScorersAdapter.ViewHolder holder, int position) {
        holder.scorerName.setText(scorerList.get(position).getPlayer().getName());
        holder.scorerTeam.setText(scorerList.get(position).getTeam().getName());
        holder.scorerNationality.setText(scorerList.get(position).getPlayer().getNationality());
        holder.scorerGoals.setText(scorerList.get(position).getNumberOfGoals());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return scorerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView scorerName;
        private final TextView scorerTeam;
        private final TextView scorerNationality;
        private final TextView scorerGoals;

        ViewHolder(View itemView) {
            super(itemView);
            scorerName = itemView.findViewById(R.id.scorerNameId);
            scorerTeam = itemView.findViewById(R.id.scorerTeamId);
            scorerNationality = itemView.findViewById(R.id.scorerNationalityId);
            scorerGoals = itemView.findViewById(R.id.scorerGoalsId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Scorer getItem(int id) {
        return scorerList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(RecyclerViewScorersAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

