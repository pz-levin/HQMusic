package com.example.hqproj.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hqproj.R;
import com.example.hqproj.Service.MusicService;

import java.io.Serializable;
import java.util.Timer;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    public static ImageView mSinger_image;
    public static TextView mSong;
    public static TextView mSinger;
    public static ImageButton mBtn_loop;
    public static ImageButton mBtn_pause;
    public static SeekBar mSeekBar;
    public static TextView mCurrentTv;
    public static TextView mTotalTv;

    private ImageView singer_image;
    private ImageButton btn_pause;
    private ImageButton btn_plus;
    private ImageButton btn_minus;
    private ImageButton btn_loop;
    private TextView song;
    private TextView singer;
    private SeekBar seekBar;
    private TextView currentTv;
    private TextView totalTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_page);
        //初始化
        initData();
        initListener();

        //点击某个item就播放
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("action", "play");
        startService(intent);
    }

    private void initData() {
        btn_pause = findViewById(R.id.btn_pause);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_loop = findViewById(R.id.btn_loop);
        singer_image = findViewById(R.id.singer_image);
        song = findViewById(R.id.song);
        singer = findViewById(R.id.singer);
        seekBar = findViewById(R.id.seekBar);
        currentTv = findViewById(R.id.currentTv);
        totalTv = findViewById(R.id.totalTv);

        mSinger_image = singer_image;
        mSong = song;
        mSinger = singer;
        mBtn_loop = btn_loop;
        mBtn_pause = btn_pause;
        mSeekBar = seekBar;
        mCurrentTv = currentTv;
        mTotalTv = totalTv;
    }

    private void initListener() {
        btn_pause.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_loop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MusicService.class);
        switch (view.getId()) {
            case R.id.btn_pause:
                intent.putExtra("action", "pause");
                startService(intent);
                break;

            case R.id.btn_plus:
                intent.putExtra("action", "plus");
                startService(intent);
                break;

            case R.id.btn_minus:
                intent.putExtra("action", "minus");
                startService(intent);
                break;
            case R.id.btn_loop:
                intent.putExtra("action", "loop");
                startService(intent);
                break;
        }
    }
}