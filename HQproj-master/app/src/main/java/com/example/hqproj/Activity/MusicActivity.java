package com.example.hqproj.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hqproj.R;
import com.example.hqproj.Service.MusicService;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    public static ImageView mSinger_image;
    public static TextView mSong;
    public static TextView mSinger;

    private ImageButton btn_play;
    private ImageButton btn_pause;
    private ImageButton btn_stop;
    private ImageButton btn_plus;
    private ImageButton btn_minus;
    private ImageView singer_image;
    private TextView song;
    private TextView singer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_page);

        btn_play =  findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        singer_image = findViewById(R.id.singer_image);
        song = findViewById(R.id.song);
        singer = findViewById(R.id.singer);
        mSinger_image = singer_image;
        mSong = song;
        mSinger = singer;

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);

        //点击某个item就播放
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("action","play");
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MusicService.class);
        switch (view.getId()) {
            case R.id.btn_play:
                intent.putExtra("action", "play");
                startService(intent);
                break;

            case R.id.btn_pause:
                intent.putExtra("action", "pause");
                startService(intent);
                break;

            case R.id.btn_stop:
                intent.putExtra("action", "stop");
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

        }
    }


}



