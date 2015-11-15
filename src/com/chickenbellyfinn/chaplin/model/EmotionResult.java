package com.chickenbellyfinn.chaplin.model;

/**
 * Created by Akshay on 11/14/2015.
 */
public class EmotionResult {

    public int faceCount;
    public String emotion;

    public EmotionResult(int count, String emo){
        this.faceCount = count;
        this.emotion = emo;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || !(other instanceof EmotionResult)){
            return false;
        }

        EmotionResult otherEmo = (EmotionResult)other;

        if(otherEmo.faceCount == faceCount && emotion.equals(otherEmo.emotion)){
            return true;
        } else {
            return false;
        }
    }
}
