package com.example.thelyrics.adapter.billboardRank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.thelyrics.activity_lyric;
import com.example.thelyrics.adapter.IntentKey;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillboardAPIConfigure implements onClickTitleBillboard {
    private final List<Billboard> billboardList;
    private final BillboardAdapter billboardAdapter;
    private final Context context;

    public BillboardAPIConfigure(Context context) {
        this.context = context;
        billboardList = new ArrayList<>();
        billboardAdapter = new BillboardAdapter(billboardList, context, this);
    }

    public BillboardAdapter getBillboardAdapter() {
        return billboardAdapter;
    }

    public void getTopTracks (ShimmerFrameLayout shimmerFrameLayout) {
        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        final String url = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=c000f3d64ff5fc28122f4892e2e36762&format=json";

        JsonObjectRequest requestTopTracks =
                new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                JSONObject firstGate = response.getJSONObject("tracks");
                                JSONArray secondGate = firstGate.getJSONArray("track");

                                for (int i = 0; i < secondGate.length(); i++) {
                                    JSONObject currData = secondGate.getJSONObject(i);

                                    String currRank = String.valueOf(i + 1);
                                    String currTitle = currData.getString("name");
                                    String currArtist = currData.getJSONObject("artist").getString("name");

                                    Billboard billboard = new Billboard();
                                    billboard.setRank(currRank);
                                    billboard.setTitle(currTitle);
                                    billboard.setArtist(currArtist);
                                    billboardList.add(billboard);
                                    getBillboardAdapter().notifyDataSetChanged();
                                    Log.i("getTopTracksJsonResponse", "Data successfully created");
                                }
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("getTopTracksJSONException", e.toString() );
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            Log.e("getTopTracksResponseError", error.toString());
                        }
                );
        requestQueue.add(requestTopTracks);
    }

    @Override
    public void onClickBillboard(Billboard billboard) {
        IntentKey intentKey = new IntentKey();

        Intent intent = new Intent(context, activity_lyric.class);
        intent.putExtra(intentKey.getOnClickQueryTitle(), billboard.getTitle());
        intent.putExtra(intentKey.getOnClickQueryArtist(), billboard.getArtist());
        context.startActivity(intent);
    }
}
