package com.example.hqproj.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.hqproj.Activity.MyAdapter;
import com.example.hqproj.beans.Datas;

public class MusicService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        if ("play".equals(action)) {
            selectMusic();
        } else if ("pause".equals(action)) {
            pauseMusic();
        } else if ("stop".equals(action)) {
            stopMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private MediaPlayer player;

    //播放
    public void playMusic(int resId) {
        if (player == null) {
            player = MediaPlayer.create(this, resId);
        }
        player.start();
    }

    //暂停
    private void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    //停止
    private void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

    //退出
    private void exitMusic() {
        stopMusic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    //选择音乐
    private void selectMusic() {
        if (player == null) {
            switch (MyAdapter.mPosition) {
                case 0:
                    playMusic(Datas.song[0]);
                case 1:
                    playMusic(Datas.song[1]);
                case 2:
                    playMusic(Datas.song[2]);
                case 3:
                    playMusic(Datas.song[3]);
                case 4:
                    playMusic(Datas.song[4]);
                case 5:
                    playMusic(Datas.song[5]);
                case 6:
                    playMusic(Datas.song[6]);
                case 7:
                    playMusic(Datas.song[7]);
                case 8:
                    playMusic(Datas.song[8]);
                case 9:
                    playMusic(Datas.song[9]);
                default:
                    playMusic(Datas.song[1]);
            }
        }
    }
}