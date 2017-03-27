package com.vunke.education.model;

import android.graphics.drawable.Drawable;

/**
 * Created by zhuxi on 2017/3/25.
 */
public class VideoListBean {
    private String VideoPath;
    private String VideoName;
    private Drawable VideoDrawable;
    private String VideoImgPath;
    private String VideoTime;

    public Drawable getVideoDrawable() {
        return VideoDrawable;
    }

    public void setVideoDrawable(Drawable videoDrawable) {
        VideoDrawable = videoDrawable;
    }

    public String getVideoImgPath() {
        return VideoImgPath;
    }

    public void setVideoImgPath(String videoImgPath) {
        VideoImgPath = videoImgPath;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public String getVideoTime() {
        return VideoTime;
    }

    public void setVideoTime(String videoTime) {
        VideoTime = videoTime;
    }
}
