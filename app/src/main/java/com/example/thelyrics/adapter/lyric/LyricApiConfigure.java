package com.example.thelyrics.adapter.lyric;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

public class LyricApiConfigure {
    private final Context context;

    public LyricApiConfigure(Context context) {
        this.context = context;
    }


    public void getLyric(String title, String artist, TextView lyricPlaceholder, ShimmerFrameLayout shimmerFrameLayout) {
        final String url = "https://api.lyrics.ovh/v1/" + artist + "/" + title;

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024 );
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        String lyric = response.getString("lyrics");
                        lyricPlaceholder.setText(lyric);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }, error -> {
                        lyricPlaceholder.setText("Sorry... we currently don't have the lyrics of this song.");
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
            );

        requestQueue.add(jsonObjectRequest);
    }
}
