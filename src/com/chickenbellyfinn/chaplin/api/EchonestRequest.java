package com.chickenbellyfinn.chaplin.api;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

/**
 * Created by Akshay on 11/14/2015.
 */
public class EchonestRequest {

//    private boolean danceabilityEnabled = false;
//    private boolean surpriseEnabled = false;
//    private boolean speechinessEnabled = false;
    private boolean valenceEnabled = false;

    private double minValence = 0;
    private double maxValence = 1;

    public void setValence(double min, double max){
        valenceEnabled = true;
        minValence = min;
        maxValence = max;
    }


    public void applyParams(GetRequest target){
        if(valenceEnabled) {
            target.queryString("min_valence", String.format("%02d", minValence));
            target.queryString("max_valence", String.format("%02d", maxValence));
        }
    }

}
