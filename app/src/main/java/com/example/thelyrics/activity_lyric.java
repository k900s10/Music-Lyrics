package com.example.thelyrics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thelyrics.adapter.lyric.LyricApiConfigure;
import com.example.thelyrics.adapter.IntentKey;
import com.facebook.shimmer.ShimmerFrameLayout;

public class activity_lyric extends AppCompatActivity {
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        navigationUpDesign();

        shimmerFrameLayout = findViewById(R.id.shimmerLyric);
        TextView lyricPlaceholder = findViewById(R.id.lyric_lyric);
        TextView titlePlaceholder = findViewById(R.id.lyric_title);
        TextView artistPlaceholder = findViewById(R.id.lyric_artist);

        IntentKey intentKey = new IntentKey();
        String title = getIntent().getStringExtra(intentKey.getOnClickQueryTitle());
        String artist = getIntent().getStringExtra(intentKey.getOnClickQueryArtist());
        Log.i("getIntent", "current title & artist: " + title + " by " + artist);

        titlePlaceholder.setText(title);
        artistPlaceholder.setText(artist);

        LyricApiConfigure lyricApiConfigure = new LyricApiConfigure(this);
        lyricApiConfigure.getLyric(title, artist, lyricPlaceholder, shimmerFrameLayout);
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

    //creating cancel button on the left of actionbar
    private void navigationUpDesign() {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lyric_menu, menu);

        MenuItem homeButton = menu.findItem(R.id.home_menu_button);
        homeButton.setOnMenuItemClickListener(v -> {
            Intent endSearchTitleActivity = new Intent("finish_activity");
            sendBroadcast(endSearchTitleActivity);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}