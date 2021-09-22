package com.example.hqproj.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.hqproj.Activity.MusicActivity;
import com.example.hqproj.Activity.MyAdapter;
import com.example.hqproj.R;
import com.example.hqproj.beans.Datas;

public class MusicService extends Service {

    private  MediaPlayer player;

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
        } else if ("loop".equals(action)) {
            loopMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //播放
    private void playMusic(int resId) {
        if (player == null) {
            player = MediaPlayer.create(this, resId);
            Log.d("sssssssss", "playing......."+MyAdapter.mPosition);

//        }else if(player.isPlaying()){
//            stopMusic();
//            Log.d("sssssssss", "重新播放"+MyAdapter.mPosition);
//            playMusic(resId);
//        }else{
        }
        player.start();
        //播放结束自动播放下一首
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                player.start();
                nextMusic();
            }
        });
    }

    //暂停
    private void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();
            Log.d("sssssssss", "pause......."+MyAdapter.mPosition);
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
        Log.d("sssssssss", "stop.......");
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
        Log.d("sssssssss", "next.......");
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
        Log.d("sssssssss", "previous.......");
    }

    //单曲循环
    private void loopMusic() {
        if(player != null && player.isPlaying()){
            player.setLooping(true);
            Toast.makeText(this, "单曲循环", Toast.LENGTH_SHORT).show();
            MusicActivity.mBtn_loop.setImageResource(R.drawable.ic_loop);
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
            case 10:
                changeImg(Datas.imgs[10]);
                changeSongName(Datas.songs[10]);
                changeSinger(Datas.singers[10]);
                playMusic(Datas.song[10]);
                break;
            case 11:
                changeImg(Datas.imgs[11]);
                changeSongName(Datas.songs[11]);
                changeSinger(Datas.singers[11]);
                playMusic(Datas.song[11]);
                break;
            case 12:
                changeImg(Datas.imgs[12]);
                changeSongName(Datas.songs[12]);
                changeSinger(Datas.singers[12]);
                playMusic(Datas.song[12]);
                break;
            default:
                Toast.makeText(this, "当前无歌曲！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

