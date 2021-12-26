package com.example.thelyrics.adapter.billboardRank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thelyrics.R;

import java.util.List;
import java.util.Random;

public class BillboardAdapter extends RecyclerView.Adapter<BillboardAdapter.ViewHolder> {
    //billboardList & context is final because the system keep recommend it to me
    private final List<Billboard> billboardList;
    private final Context context;
    private final onClickTitleBillboard onClick;

    //current color list of ranking background
    int[] rankBackgroundColor = {
            R.drawable.ic_circle_blue, R.drawable.ic_circle_green, R.drawable.ic_circle_grey,
            R.drawable.ic_circle_orange, R.drawable.ic_circle_pink, R.drawable.ic_circle_purple,
            R.drawable.ic_circle_red, R.drawable.ic_circle_teal, R.drawable.ic_circle_yellow};

    public BillboardAdapter(List<Billboard> billboardList, Context context, onClickTitleBillboard onClick) {
        this.billboardList = billboardList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_chart_billboard, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Billboard currentData = billboardList.get(position);

        holder.billboardRank.setText(currentData.getRank());
        holder.billboardSong.setText(currentData.getTitle());
        holder.billboardArtist.setText(currentData.getArtist());

        //creating random ranking background color
        Random random = new Random();
        int randInt = random.nextInt(9);
        holder.billboardRank.setBackgroundResource(rankBackgroundColor[randInt]);

        holder.cardView.setOnClickListener(v -> onClick.onClickBillboard(currentData));

    }

    @Override
    public int getItemCount() {
        return billboardList != null ? billboardList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView billboardRank, billboardSong, billboardArtist;
        CardView cardView;
        BillboardAdapter billboardAdapter;

        public ViewHolder(@NonNull View itemView, BillboardAdapter billboardAdapter) {
            super(itemView);
            this.billboardRank = itemView.findViewById(R.id.billboard_rank);
            this.billboardSong = itemView.findViewById(R.id.billboard_song);
            this.billboardArtist = itemView.findViewById(R.id.billboard_artist);
            this.cardView = itemView.findViewById(R.id.cardViewChartBillboard);
            this.billboardAdapter = billboardAdapter;
        }
    }
}
