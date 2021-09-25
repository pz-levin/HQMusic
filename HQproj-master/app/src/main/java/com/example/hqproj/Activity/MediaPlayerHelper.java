package com.example.hqproj.Activity;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerHelper extends MediaPlayer{

    private static MediaPlayerHelper instance;
    private Context mContext;//上下文对象
    private MediaPlayer mMediaPlayer;//MediaPlayer媒体类

    public static MediaPlayerHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper();
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    private MediaPlayerHelper() {
        mMediaPlayer = new MediaPlayer();
    }
}