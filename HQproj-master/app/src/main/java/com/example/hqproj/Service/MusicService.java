package com.example.hqproj.Service;

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
import com.example.hqproj.R;
import com.example.hqproj.beans.Datas;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.hqproj.Activity.MusicActivity.mCurrentTv;
import static com.example.hqproj.Activity.MusicActivity.mSeekBar;
import static com.example.hqproj.Activity.MusicActivity.mTotalTv;

public class MusicService extends Service implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnPreparedListener {

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
//        intent.getSerializableExtra("song");
//        this.song = song;

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

    //播放
    private void playMusic(int resId) {
        if (player != null) {
            stopMusic();
        }
        player = MediaPlayer.create(this, resId);
        player.start();
//        currentPos = player.getCurrentPosition();
//        player.setOnPreparedListener(this);
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(isSeekBarChanging){
//                    return;
//                }
//                mSeekBar.setProgress(player.getCurrentPosition());
//            }
//        },0,50);
        MusicActivity.mBtn_pause.setImageResource(R.drawable.ic_play);
        RecyclerViewActivity.mSongButton.setImageResource(R.drawable.ic_play1);

        mSeekBar.setOnSeekBarChangeListener(this);

        //播放结束自动播放下一首
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextMusic();
            }
        });
    }

    //暂停
    private void pauseMusic() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
                Log.d("dddddddddd", "pause--->" + player.toString());
                MusicActivity.mBtn_pause.setImageResource(R.drawable.ic_pause);
                RecyclerViewActivity.mSongButton.setImageResource(R.drawable.ic_pause1);
            } else {
                player.start();
                MusicActivity.mBtn_pause.setImageResource(R.drawable.ic_play);
                RecyclerViewActivity.mSongButton.setImageResource(R.drawable.ic_play1);
            }
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
        MusicActivity.mBtn_loop.setImageResource(R.drawable.ic_loop_list);
        //获取当前播放的是哪个item
        int currentResId = MyAdapter.mPosition + 1;
        if (currentResId >= 0 && currentResId < Datas.song.length) {
            stopMusic();
            playMusic(Datas.song[currentResId++]);
            currentResId--;
            changeImg(Datas.imgs[currentResId++]);//图片
            currentResId--;
            changeSinger(Datas.singers[currentResId++]);//歌手
            currentResId--;
            changeSongName(Datas.songs[currentResId++]);//歌名
            MyAdapter.mPosition++;
        } else if (currentResId == Datas.song.length) {
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
        MusicActivity.mBtn_loop.setImageResource(R.drawable.ic_loop_list);
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

    //循环
    private void loopMusic() {
        MusicActivity.mBtn_loop.setImageResource(R.drawable.ic_loop_list);
        if (player != null) {
            //列表循环
            if (player.isLooping()) {
                player.setLooping(false);
                Toast.makeText(this, "列表循环", Toast.LENGTH_SHORT).show();
            } else {
                //单曲循环
                player.setLooping(true);
                Toast.makeText(this, "单曲循环", Toast.LENGTH_SHORT).show();
                MusicActivity.mBtn_loop.setImageResource(R.drawable.ic_loop);
            }
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
        timer.cancel();
        timer = null;
    }

    //更换图片
    private void changeImg(int resId) {
        MusicActivity.mSinger_image.setImageResource(resId);
        RecyclerViewActivity.mSingerImg.setImageResource(resId);
    }

    //更换歌名
    private void changeSongName(String resId) {
        MusicActivity.mSong.setText(resId);
        RecyclerViewActivity.mSongTv.setText(resId);
    }

    //更换歌手
    private void changeSinger(String resId) {
        MusicActivity.mSinger.setText(resId);
        RecyclerViewActivity.mSingerTv.setText(resId);
    }

    //选择音乐
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
                Toast.makeText(this, "当前无歌曲！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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

    @Override
    public void onPrepared(MediaPlayer mp) {
        player.seekTo(currentPos);
        mSeekBar.setMax(player.getDuration());
    }
}