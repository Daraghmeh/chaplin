package com.chickenbellyfinn.chaplin;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;

/**
 * Created by Akshay on 11/14/2015.
 */
public class ImageView implements Kinect.KinectView {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    BufferedImage image;

    private JFrame jFrame;
    private JPanel panel;

    public ImageView(){
        jFrame = new JFrame();
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.add(panel);
        jFrame.setVisible(true);
    }

    private void paintPanel(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        if(image != null) {
            g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        }


    }

    private void createUIComponents() {
        panel = new JPanel(){
          @Override
            public void paintComponent(Graphics g){
              paintPanel(g);
          }
        };
    }

    @Override
    public void setImage(byte[] frame, int w, int h) {

        try {
            image = ImageIO.read(new ByteArrayInputStream(frame));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
