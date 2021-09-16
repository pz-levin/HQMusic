
package com.example.hqproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_play;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        btn_play =  findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_exit = findViewById(R.id.btn_exit);

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
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

            case R.id.btn_exit:
                intent.putExtra("action", "exit");
                stopService(intent);
                finish();
                break;
        }
    }


}

