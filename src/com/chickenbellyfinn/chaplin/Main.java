package com.chickenbellyfinn.chaplin;

import edu.ufl.digitalworlds.j4k.J4KSDK;

import javax.swing.*;

/**
 * Created by Akshay on 11/14/2015.
 */
public class Main {

    public static void main(String[] args){

        Kinect kinect = new Kinect();

        EmotionMonitor emotionMonitor = new EmotionMonitor(kinect);
        ImagePanelGL imagePanel = new ImagePanelGL();


        kinect.start(J4KSDK.COLOR);
        kinect.setView(imagePanel);
        emotionMonitor.start();
    }
}
