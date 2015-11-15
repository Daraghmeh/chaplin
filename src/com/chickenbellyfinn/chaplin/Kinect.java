package com.chickenbellyfinn.chaplin;

import edu.ufl.digitalworlds.j4k.J4KSDK;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class Kinect extends J4KSDK {

    public byte[] lastFrame = null;

    interface KinectView {
        public void setImage(byte[] frame, int w, int h);
    }

    private KinectView view;

    public void setView(KinectView view) {
        this.view = view;
    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {}

    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions,float[] orientations, byte[] joint_status) {}

    @Override
    public void onColorFrameEvent(byte[] color_frame) {
        this.lastFrame = color_frame;
        if(view != null){
            view.setImage(color_frame, getColorWidth(), getColorHeight());
        }
    }

    public byte[] getLastFrameJPG(){
        byte[] bgra = this.lastFrame;

        if(bgra == null){
            return null;
        }

        BufferedImage imageBuf = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);

        int r = 0, g = 0, b = 0;
        int rgb;

        for (int x = 0; x < 1920; x++) {
            for (int y = 0; y < 1080; y++) {
                r = (bgra[4 * (1920 * y + x) + 2] & 0xFF) << 16;
                g = (bgra[4 * (1920 * y + x) + 1] & 0xFF) << 8;
                b = (bgra[4 * (1920 * y + x) + 0] & 0xFF);
                rgb = r | g | b;
                imageBuf.setRGB(x, y, rgb);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(imageBuf, "JPG", outputStream);
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }
}
