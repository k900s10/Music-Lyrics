package com.example.thelyrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thelyrics.adapter.searchTitles.SearchTitlesAPIConfigure;

public class search_title extends AppCompatActivity {
    private SearchTitlesAPIConfigure searchTitlesAPIConfigure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_title);
        navigationUpDesign();

        EditText queryInput = findViewById(R.id.queryInput);
        TextView resultTitle = findViewById(R.id.resultTitle);
        View querySearchLineBreak = findViewById(R.id.querySearchLineBreak);
        View queryResultLineBreak = findViewById(R.id.queryResultLineBreak);

        searchTitlesAPIConfigure = new SearchTitlesAPIConfigure(this);
        configureRecycleView();

        queryInput.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                searchTitlesAPIConfigure.getClearData();
                String queryInputValue = String.valueOf(queryInput.getText());
                searchTitlesAPIConfigure.getTitleList(queryInputValue);

                querySearchLineBreak.setVisibility(View.VISIBLE);
                resultTitle.setVisibility(View.VISIBLE);
                queryResultLineBreak.setVisibility(View.VISIBLE);
            }
            return false;
        });

        finishActivity();
    }

    private void finishActivity() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    Log.i("BroadcastReceiver", "onReceive: a signal is hitting my antenna!");
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }

    //creating cancel button on the left of actionbar
    private void navigationUpDesign() {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void configureRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.queryResultRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(searchTitlesAPIConfigure.getQueryTitlesAdapter());

    }
}