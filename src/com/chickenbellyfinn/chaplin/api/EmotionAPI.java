package com.chickenbellyfinn.chaplin.api;

import com.chickenbellyfinn.chaplin.Stopwatch;
import com.chickenbellyfinn.chaplin.model.EmotionResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;

import com.sun.prism.paint.Stop;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Akshay on 11/14/2015.
 */
public class EmotionAPI {

    public static final String EMOTION_CONTEMPT = "contempt";
    public static final String EMOTION_SURPRISE = "surprise";
    public static final String EMOTION_HAPPINESS = "happiness";
    public static final String EMOTION_NEUTRAL = "neutral";
    public static final String EMOTION_SADNESS = "sadness";
    public static final String EMOTION_DISGUST = "disgust";
    public static final String EMOTION_ANGER = "anger";
    public static final String EMOTION_FEAR = "fear";

    public static final String[] EMOTIONS = {
            EMOTION_CONTEMPT,
            EMOTION_SURPRISE,
            EMOTION_HAPPINESS,
            EMOTION_NEUTRAL,
            EMOTION_SADNESS,
            EMOTION_DISGUST,
            EMOTION_ANGER,
            EMOTION_FEAR
    };

    private static final String API_URL = "https://api.projectoxford.ai/emotion/v1.0/recognize";
    private static final String API_KEY = "84b6b564822c4dc2a14ab3c9cb06f106";

    public static EmotionResult getEmotions(final byte[] image) {
        JSONArray emotionResponse = doGetEmotions(image);
        int faceCount = getFaceCount(emotionResponse);
        String emotion = getProminentEmotion(emotionResponse);
        return new EmotionResult(faceCount, emotion);
    }

    private static JSONArray doGetEmotions(byte[] image) {
        try {
            System.out.print("Getting faces...");
            Stopwatch.start("doGetEmotions");
            HttpResponse<JsonNode> response = Unirest.post(API_URL)
                    .header("Ocp-Apim-Subscription-Key", API_KEY)
                    .header("Content-Type", "application/octet-stream")
                    .body(image)
                    .asJson();

            JSONArray faces = response.getBody().getArray();
            //System.out.println(faces.toString());
            return faces;

        } catch (UnirestException e) {
            e.printStackTrace();
        } finally {
            Stopwatch.stop("doGetEmotions");
        }

        return new JSONArray();
    }

    private static int getFaceCount(JSONArray json) {
        return json.length();
    }

    private static String getProminentEmotion(JSONArray json) {
        HashMap<String, Integer> emotes = new HashMap<String, Integer>();
        for(String emo:EMOTIONS){
            emotes.put(emo,0);
        }

        for(int i = 0; i < json.length(); i++){
            JSONObject obj = json.getJSONObject(i).getJSONObject("scores");
            for(String emo:EMOTIONS){
                int count = emotes.get(emo);
                if(obj.getDouble(emo) > 0.5){
                    emotes.put(emo, count + 1);
                }
            }
        }

        String finalEmo = EMOTION_NEUTRAL;
        for(String emo:EMOTIONS){
            if(emotes.get(emo) > emotes.get(finalEmo)){
                finalEmo = emo;
            }
        }
        return finalEmo;
    }
}
