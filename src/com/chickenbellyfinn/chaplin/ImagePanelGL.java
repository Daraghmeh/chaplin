package com.chickenbellyfinn.chaplin;

import javax.media.opengl.GL2;
import javax.swing.*;

import edu.ufl.digitalworlds.opengl.OpenGLPanel;
import edu.ufl.digitalworlds.j4k.VideoFrame;

import java.awt.image.ImageObserver;

public class ImagePanelGL extends OpenGLPanel implements Kinect.KinectView {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 540;

    JFrame frame;
    VideoFrame videoTexture;

    boolean t = false;

    public ImagePanelGL(){

        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void setup() {

        //OPENGL SPECIFIC INITIALIZATION (OPTIONAL)
        GL2 gl=getGL2();
        gl.glEnable(GL2.GL_CULL_FACE);
        float light_model_ambient[] = {0.3f, 0.3f, 0.3f, 1.0f};
        float light0_diffuse[] = {0.9f, 0.9f, 0.9f, 0.9f};
        float light0_direction[] = {0.0f, -0.4f, 1.0f, 0.0f};
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_FALSE);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_FALSE);
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, light_model_ambient,0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse,0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_direction,0);
        gl.glEnable(GL2.GL_LIGHT0);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glColor3f(0.9f,0.9f,0.9f);


        videoTexture=new VideoFrame();


        background(0, 0, 0);
    }

    @Override
    public void draw() {
        GL2 gl=getGL2();

        pushMatrix();

        gl.glDisable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glColor3f(1f,1f,1f);
        videoTexture.use(gl);
        translate(0,0,-2.2);
        rotateZ(180);
        image((1920f/1080f)*2,2);

        popMatrix();
    }


    @Override
    public void setImage(byte[] frame, int w, int h) {
        if(videoTexture != null) {
            videoTexture.update(w, h, frame);
        }
    }
}
