package com.example.hqproj.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.hqproj.Activity.MusicActivity;
import com.example.hqproj.Activity.MyAdapter;
import com.example.hqproj.beans.Datas;


public class MusicService extends Service {

    private MediaPlayer player;

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
        } else if ("plus".equals(action)) {
            nextMusic();
        } else if ("minus".equals(action)) {
            previousMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //播放
    private void playMusic(int resId) {
        if (player == null) {
            player = MediaPlayer.create(this, resId);
            //如果该歌曲正在播放时想播放其他歌
        }else if(player.isPlaying()){
            stopMusic();
            playMusic(resId);
        }
        player.start();
//        else{
//            stopMusic();
//            playMusic(resId);
//        }

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

    //下一首
    private void nextMusic() {
        //获取当前播放的是哪个item
        int currentResId = MyAdapter.mPosition + 1;
        if (currentResId >= 0 && currentResId < Datas.song.length) {
            Log.d("aaaaaaaaaa", "currentId 1 --->" + currentResId);
            stopMusic();
            playMusic(Datas.song[currentResId++]);
            Log.d("aaaaaaaaaa", "currentId 2 --->" + currentResId);
            currentResId--;
            changeImg(Datas.imgs[currentResId++]);//图片
            currentResId--;
            changeSinger(Datas.singers[currentResId++]);//歌手
            currentResId--;
            changeSongName(Datas.songs[currentResId++]);//歌名
            MyAdapter.mPosition++;
        } else if (currentResId == Datas.song.length) {
            Log.d("aaaaaaaaaa", "currentId 3 --->" + currentResId);
            MyAdapter.mPosition = 0;
            stopMusic();
            playMusic(Datas.song[0]);
            changeImg(Datas.imgs[0]);
            changeSinger(Datas.singers[0]);
            changeSongName(Datas.songs[0]);
        }
    }

    //上一首
    private void previousMusic() {
        //获取当前播放的是哪个item
        int currentResId = MyAdapter.mPosition;
        if (currentResId > 0) {
            stopMusic();
            playMusic(Datas.song[--currentResId]);//上一首歌曲
            currentResId++;
            changeImg(Datas.imgs[--currentResId]);//图片
            currentResId++;
            changeSinger(Datas.singers[--currentResId]);//歌手
            currentResId++;
            changeSongName(Datas.songs[--currentResId]);//歌名
            MyAdapter.mPosition--;
        } else if (currentResId == 0) {
            MyAdapter.mPosition = Datas.song.length - 1;
            stopMusic();
            playMusic(Datas.song[Datas.song.length - 1]);
            changeImg(Datas.imgs[Datas.imgs.length - 1]);
            changeSinger(Datas.singers[Datas.singers.length - 1]);
            changeSongName(Datas.songs[Datas.songs.length - 1]);
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

    //更换图片
    private void changeImg(int resId) {
        MusicActivity.mSinger_image.setImageResource(resId);
    }

    //更换歌名
    private void changeSongName(String resId) {
        MusicActivity.mSong.setText(resId);
    }

    //更换歌手
    private void changeSinger(String resId) {
        MusicActivity.mSinger.setText(resId);
    }

    //选择音乐
    private void selectMusic() {
        switch (MyAdapter.mPosition) {
            case 0:
                changeImg(Datas.imgs[0]);
                changeSongName(Datas.songs[0]);
                changeSinger(Datas.singers[0]);
                playMusic(Datas.song[0]);
                break;
            case 1:
                changeImg(Datas.imgs[1]);
                changeSongName(Datas.songs[1]);
                changeSinger(Datas.singers[1]);
                playMusic(Datas.song[1]);
                break;
            case 2:
                changeImg(Datas.imgs[2]);
                changeSongName(Datas.songs[2]);
                changeSinger(Datas.singers[2]);
                playMusic(Datas.song[2]);
                break;
            case 3:
                changeImg(Datas.imgs[3]);
                changeSongName(Datas.songs[3]);
                changeSinger(Datas.singers[3]);
                playMusic(Datas.song[3]);
                break;
            case 4:
                changeImg(Datas.imgs[4]);
                changeSongName(Datas.songs[4]);
                changeSinger(Datas.singers[4]);
                playMusic(Datas.song[4]);
                break;
            case 5:
                changeImg(Datas.imgs[5]);
                changeSongName(Datas.songs[5]);
                changeSinger(Datas.singers[5]);
                playMusic(Datas.song[5]);
                break;
            case 6:
                changeImg(Datas.imgs[6]);
                changeSongName(Datas.songs[6]);
                changeSinger(Datas.singers[6]);
                playMusic(Datas.song[6]);
                break;
            case 7:
                changeImg(Datas.imgs[7]);
                changeSongName(Datas.songs[7]);
                changeSinger(Datas.singers[7]);
                playMusic(Datas.song[7]);
                break;
            case 8:
                changeImg(Datas.imgs[8]);
                changeSongName(Datas.songs[8]);
                changeSinger(Datas.singers[8]);
                playMusic(Datas.song[8]);
                break;
            case 9:
                changeImg(Datas.imgs[9]);
                changeSongName(Datas.songs[9]);
                changeSinger(Datas.singers[9]);
                playMusic(Datas.song[9]);
                break;
            default:
                Toast.makeText(this, "当前无歌曲！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

