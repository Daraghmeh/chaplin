package com.chickenbellyfinn.chaplin.api;

import com.chickenbellyfinn.chaplin.Stopwatch;
import com.chickenbellyfinn.chaplin.model.Song;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 11/14/2015.
 */
public class EchonestAPI {

    private static final String API_URL = "http://developer.echonest.com/api/v4/song/search";
    private static final String API_KEY = "XOBZGRFB04BFSTTQ9";

    private static final int RESULT_COUNT = 20;
    private static final int PLAYLIST_CAP = 10;

    public static List<Song> search(EchonestRequest request) {
        //sort by artist familiaroty
        Stopwatch.start("Echonest search");
        GetRequest searchRequest = Unirest.get(API_URL);
        searchRequest.queryString("api_key", API_KEY);
        searchRequest.queryString("results", ""+RESULT_COUNT);
        searchRequest.queryString("sort", "artist_familiarity-desc");
        request.applyParams(searchRequest);

        System.out.println(searchRequest.getUrl());

        JSONArray songList;
        try {
            songList = searchRequest.asJson().getBody().getObject().getJSONObject("response").getJSONArray("songs");

            List<Song> searchResults = new ArrayList<Song>();

            for(int i = 0; i < songList.length(); i++){
                JSONObject song = songList.getJSONObject(i);
                String artist = song.getString("artist_name");
                String title = song.getString("title");
                searchResults.add(new Song(title, artist));

                if(searchResults.size() > PLAYLIST_CAP) {
                    break;
                }
            }

            return searchResults;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Stopwatch.stop("Echonest search");
        }

        return null;
    }
}
