package com.example.hqproj.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hqproj.Activity.MusicActivity;
import com.example.hqproj.Activity.MyAdapter;
import com.example.hqproj.Activity.RecyclerViewActivity;
import com.example.hqproj.beans.Datas;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.hqproj.Activity.MusicActivity.LOOP_LIST;
import static com.example.hqproj.Activity.MusicActivity.LOOP_SINGLE;
import static com.example.hqproj.Activity.MusicActivity.PAUSE;
import static com.example.hqproj.Activity.MusicActivity.PLAY;
import static com.example.hqproj.Activity.MusicActivity.mCurrentTv;
import static com.example.hqproj.Activity.MusicActivity.mSeekBar;
import static com.example.hqproj.Activity.MusicActivity.mTotalTv;
import static com.example.hqproj.Activity.RecyclerViewActivity.PAUSE1;
import static com.example.hqproj.Activity.RecyclerViewActivity.PLAY1;

public class MusicService extends Service implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer player;
    private boolean isSeekBarChanging;
    private int currentPos;
    private Timer timer;
    private TextView song;

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
        } else if ("plus".equals(action)) {
            nextMusic();
        } else if ("minus".equals(action)) {
            previousMusic();
        } else if ("loop".equals(action)) {
            loopMusic();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    //??????
    Intent i = new Intent();
    Intent i2 = new Intent();
    private void playMusic(int resId) {
        if (player != null) {
            stopMusic();
        }
        player = MediaPlayer.create(this, resId);
        player.start();
        player.setOnPreparedListener(mp -> {
            mSeekBar.setMax(player.getDuration());
        });
        currentPos = player.getCurrentPosition();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isSeekBarChanging){
                    mSeekBar.setProgress(currentPos);
                }
            }
        },0,50);
        mSeekBar.setOnSeekBarChangeListener(this);
        i.setAction(PLAY);//??????????????????UI???????????????
        i2.setAction(PLAY1);
        sendBroadcast(i);
        sendBroadcast(i2);

        //?????????????????????????????????
        player.setOnCompletionListener(mp -> nextMusic());
    }

    //??????
    private void pauseMusic() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
                i.setAction(PAUSE);//??????????????????UI???????????????
                i2.setAction(PAUSE1);
            } else {
                player.start();
                i.setAction(PLAY);//??????????????????UI???????????????
                i2.setAction(PLAY1);
            }
        }
        sendBroadcast(i);
        sendBroadcast(i2);
    }

    //??????
    private void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

    //?????????
    private void nextMusic() {
        i.setAction(LOOP_LIST);//??????????????????UI???????????????
        //??????????????????????????????item
        int currentResId = MyAdapter.mPosition + 1;
        if (currentResId >= 0 && currentResId < Datas.song.length) {
            stopMusic();
            playMusic(Datas.song[currentResId++]);
            currentResId--;
            changeImg(Datas.imgs[currentResId++]);//??????
            currentResId--;
            changeSinger(Datas.singers[currentResId++]);//??????
            currentResId--;
            changeSongName(Datas.songs[currentResId++]);//??????
            MyAdapter.mPosition++;
        } else if (currentResId == Datas.song.length) {
            MyAdapter.mPosition = 0;
            stopMusic();
            playMusic(Datas.song[0]);
            changeImg(Datas.imgs[0]);
            changeSinger(Datas.singers[0]);
            changeSongName(Datas.songs[0]);
        }
        sendBroadcast(i);
    }

    //?????????
    private void previousMusic() {
        i.setAction(LOOP_LIST);//??????????????????UI???????????????
        //??????????????????????????????item
        int currentResId = MyAdapter.mPosition;
        if (currentResId > 0) {
            stopMusic();
            playMusic(Datas.song[--currentResId]);//???????????????
            currentResId++;
            changeImg(Datas.imgs[--currentResId]);//??????
            currentResId++;
            changeSinger(Datas.singers[--currentResId]);//??????
            currentResId++;
            changeSongName(Datas.songs[--currentResId]);//??????
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
        sendBroadcast(i);
    }

    //??????
    private void loopMusic() {
        i.setAction(LOOP_LIST);//??????????????????UI???????????????
        if (player != null) {
            //????????????
            if (player.isLooping()) {
                player.setLooping(false);
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            } else {
                //????????????
                player.setLooping(true);
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                i.setAction(LOOP_SINGLE);//??????????????????UI???????????????
            }
        }
        sendBroadcast(i);
    }

    //??????
    private void exitMusic() {
        stopMusic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isSeekBarChanging = true;
//        stopMusic();
//        timer.cancel();
//        timer = null;
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    //????????????
    private void changeImg(int resId) {
        MusicActivity.mSinger_image.setImageResource(resId);
        RecyclerViewActivity.mSingerImg.setImageResource(resId);
    }

    //????????????
    private void changeSongName(String resId) {
        MusicActivity.mSong.setText(resId);
        RecyclerViewActivity.mSongTv.setText(resId);
    }

    //????????????
    private void changeSinger(String resId) {
        MusicActivity.mSinger.setText(resId);
        RecyclerViewActivity.mSingerTv.setText(resId);
    }

    //????????????
    private void selectMusic() {
        switch (MyAdapter.mPosition) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                changeImg(Datas.imgs[MyAdapter.mPosition]);
                changeSongName(Datas.songs[MyAdapter.mPosition]);
                changeSinger(Datas.singers[MyAdapter.mPosition]);
                playMusic(Datas.song[MyAdapter.mPosition]);
                break;
            default:
                Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();

                break;

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        String musicTotalTime=sdf.format(player.getDuration());
        String musicCurrentTime = sdf.format(player.getCurrentPosition());
        mCurrentTv.setText("" + musicCurrentTime);
        mTotalTv.setText("" + musicTotalTime);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeekBarChanging = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeekBarChanging = false;
        player.seekTo(seekBar.getProgress());
    }
}