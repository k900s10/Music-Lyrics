package com.example.thelyrics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thelyrics.adapter.billboardRank.BillboardAPIConfigure;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {
    private BillboardAPIConfigure chartBillboard;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = findViewById(R.id.shimmerMain);

        chartBillboard = new BillboardAPIConfigure(this);
        configureRecycleView();
        chartBillboard.getTopTracks(shimmerFrameLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchBar = menu.findItem(R.id.app_bar_search);
        searchBar.setOnMenuItemClickListener(v -> {
            Intent intent = new Intent(this, search_title.class);
            startActivity(intent);
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void configureRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycleViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(chartBillboard.getBillboardAdapter());
    }
}