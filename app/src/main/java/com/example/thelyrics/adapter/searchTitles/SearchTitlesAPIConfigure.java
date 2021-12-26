package com.example.thelyrics.adapter.searchTitles;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
import com.example.thelyrics.adapter.lyric.onClickTitle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchTitlesAPIConfigure implements onClickTitle {
    private final List<QueryTitles> queryTitlesList;
    private final QueryTitlesAdapter queryTitlesAdapter;
    private final Context context;

    public SearchTitlesAPIConfigure(Context context) {
        this.context = context;
        this.queryTitlesList = new ArrayList<>();
        this.queryTitlesAdapter = new QueryTitlesAdapter(queryTitlesList, context, this);
    }

    public QueryTitlesAdapter getQueryTitlesAdapter() {
        return queryTitlesAdapter;
    }

    public void getClearData() {
        queryTitlesAdapter.clearData();
    }

    public void getTitleList(String queryInput) {
        RequestQueue requestQueue;

        String url = "https://ws.audioscrobbler.com/2.0/?method=track.search&track=" + queryInput + "&api_key=c000f3d64ff5fc28122f4892e2e36762&format=json";

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JsonObjectRequest requestSongList =
                new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                JSONObject firstObject = response.getJSONObject("results");
                                JSONObject secondObject = firstObject.getJSONObject("trackmatches");
                                JSONArray thirdObject = secondObject.getJSONArray("track");

                                for (int i = 0; i < thirdObject.length(); i++) {
                                    JSONObject fourthObject = thirdObject.getJSONObject(i);
                                    String currTitle = fourthObject.getString("name");
                                    String currArtist = fourthObject.getString("artist");


                                    QueryTitles currData = new QueryTitles();
                                    currData.setTitle(currTitle);
                                    currData.setArtist(currArtist);
                                    queryTitlesList.add(currData);
                                    queryTitlesAdapter.notifyDataSetChanged();
                                }
                                Log.i("getTitleListJsonResponse", "Data successfully created");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("getTitleListJSONException", e.toString() );
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            Log.e("getTitleListResponseError", error.toString());
//                            onErrorResponse(error);
                        }
                );

        requestQueue.add(requestSongList);
    }

    @Override
    public void onClickResult(QueryTitles queryTitles) {
        IntentKey intentKey = new IntentKey();

        Intent intent = new Intent(context, activity_lyric.class);
        intent.putExtra(intentKey.getOnClickQueryTitle(), queryTitles.getTitle());
        intent.putExtra(intentKey.getOnClickQueryArtist(), queryTitles.getArtist());
        context.startActivity(intent);
    }
}
