package com.example.hqproj.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hqproj.MyBroadcast;
import com.example.hqproj.R;
import com.example.hqproj.Service.MusicService;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String PLAY = "UPDATE_BUTTON_PLAY";
    public static final String PAUSE = "UPDATE_BUTTON_PAUSE";
    public static final String LOOP_LIST = "UPDATE_BUTTON_LOOP_LIST";
    public static final String LOOP_SINGLE = "UPDATE_BUTTON_LOOP_SINGLE";

    public static ImageView mSinger_image;
    public static TextView mSong;
    public static TextView mSinger;
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
    Intent intent;
    private IntentFilter intentFilter;
    private MyBroadcast b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_page);
        //初始化
        initData();
        initListener();
        //注册广播
        regBroadcast();
        //更新UI
        updateUI();
        //点击某个item就播放
        intent = new Intent(this, MusicService.class);
        intent.putExtra("action", "play");
        startService(intent);
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        b1.setOnUpdateUI(i -> btn_pause.setImageResource(R.drawable.ic_play));
        b2.setOnUpdateUI(i -> btn_pause.setImageResource(R.drawable.ic_pause));
        b3.setOnUpdateUI(i -> btn_loop.setImageResource(R.drawable.ic_loop_list));
        b4.setOnUpdateUI(i -> btn_loop.setImageResource(R.drawable.ic_loop));
    }

    /**
     * 注册广播
     */
    private void regBroadcast() {
        b1 = new MyBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction(PLAY);
        registerReceiver(b1, intentFilter);

        b2 = new MyBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction(PAUSE);
        registerReceiver(b2, intentFilter);

        b3 = new MyBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOOP_LIST);
        registerReceiver(b3, intentFilter);

        b4 = new MyBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOOP_SINGLE);
        registerReceiver(b4, intentFilter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(b1);
        unregisterReceiver(b2);
        unregisterReceiver(b3);
        unregisterReceiver(b4);
    }
}