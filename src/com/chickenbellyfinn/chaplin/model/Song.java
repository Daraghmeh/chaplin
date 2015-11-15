package com.chickenbellyfinn.chaplin.model;

/**
 * Created by Akshay on 11/14/2015.
 */
public class Song {

    public String title;
    public String artist;

    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString(){
        return String.format("%s, %s", title, artist);
    }
}
