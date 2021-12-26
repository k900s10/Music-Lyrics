package com.example.thelyrics.adapter.searchTitles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thelyrics.R;
import com.example.thelyrics.adapter.lyric.onClickTitle;

import java.util.List;

public class QueryTitlesAdapter extends RecyclerView.Adapter<QueryTitlesAdapter.ViewHolder> {
    private final List<QueryTitles> queryTitlesList;
    private final Context context;
    private final onClickTitle onClick;

    public QueryTitlesAdapter(List<QueryTitles> queryTitlesList, Context context, onClickTitle onClickTitle) {
        this.queryTitlesList = queryTitlesList;
        this.context = context;
        this.onClick = onClickTitle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_query_titles, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        QueryTitles currentData = queryTitlesList.get(position);

        holder.queryTitle.setText(currentData.getTitle());
        holder.queryArtist.setText(currentData.getArtist());

        holder.cardView.setOnClickListener(v -> onClick.onClickResult(currentData));
    }

    @Override
    public int getItemCount() {
        return queryTitlesList != null ? queryTitlesList.size() : 0;
    }

    public void clearData() {
        if (queryTitlesList != null) {
            int querySize = queryTitlesList.size();
            queryTitlesList.clear();
            notifyItemRangeRemoved(0, querySize);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView queryTitle, queryArtist;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.queryTitle = itemView.findViewById(R.id.query_title);
            this.queryArtist = itemView.findViewById(R.id.query_artist);
            this.cardView = itemView.findViewById(R.id.queryCardView);
        }
    }


}
