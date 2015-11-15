package com.chickenbellyfinn.chaplin;

import com.chickenbellyfinn.chaplin.api.EchonestAPI;
import com.chickenbellyfinn.chaplin.api.EchonestRequest;
import com.chickenbellyfinn.chaplin.api.EmotionAPI;
import com.chickenbellyfinn.chaplin.model.EmotionResult;
import com.chickenbellyfinn.chaplin.model.Song;

import java.util.List;

/**
 * Created by Akshay on 11/14/2015.
 */
public class EmotionMonitor extends Thread {

    private final long POLL_RATE = 20000;//every 5 seconds

    private Kinect kinect;
    private EmotionResult lastEmotion;

    public EmotionMonitor(Kinect kinect){
        this.kinect = kinect;
    }

    @Override
    public void run(){
        while(true){

            byte[] snapshotJPG = kinect.getLastFrameJPG();
            if(snapshotJPG != null) {
                System.out.println("Assesing the situation...");
                EmotionResult currentEmotion = EmotionAPI.getEmotions(snapshotJPG);
                System.out.printf("%d %s people\n", currentEmotion.faceCount, currentEmotion.emotion);
                if(!currentEmotion.equals(lastEmotion)){
                    lastEmotion = currentEmotion;

                    EchonestRequest params = createEchonestParams(currentEmotion);
                    List<Song> searchResults = EchonestAPI.search(params);
                    System.out.println(searchResults);
                }
            }
            try {
                Thread.sleep(POLL_RATE);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public EchonestRequest createEchonestParams(EmotionResult emotion){

        EchonestRequest request = new EchonestRequest();

        //emotion-based settings
        switch(emotion.emotion){
            case EmotionAPI.EMOTION_CONTEMPT:
                request.setValence(0.2, 0.5);
                break;
            case EmotionAPI.EMOTION_SURPRISE:
                request.setValence(0.65, 1);
                break;
            case EmotionAPI.EMOTION_HAPPINESS:
                request.setValence(0.65, 1);
                break;
            case EmotionAPI.EMOTION_NEUTRAL:
                break;
            case EmotionAPI.EMOTION_SADNESS:
                request.setValence(0, 0.45);
                break;
            case EmotionAPI.EMOTION_DISGUST:
                request.setValence(0, 0.5);
                break;
            case EmotionAPI.EMOTION_ANGER:
                request.setValence(0, 0.5);
                break;
            case EmotionAPI.EMOTION_FEAR:
                request.setValence(0, 0.35);
                break;
        }

        return request;
    }
}
