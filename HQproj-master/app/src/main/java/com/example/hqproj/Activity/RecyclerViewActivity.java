package com.example.hqproj.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqproj.R;
import com.example.hqproj.Service.MusicService;
import com.example.hqproj.beans.Datas;
import com.example.hqproj.beans.MusicBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity{

    public static TextView mSongTv;
    public static TextView mSingerTv;
    public static ImageButton mSongButton;
    public static ImageView mSingerImg;

    private RecyclerView mRc_view;
    private List<MusicBean> mList;
    private TextView songTv;
    private TextView singerTv;
    private ImageButton songButton;
    private ImageView singerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        //准备数据
        initData();
    }

    private void initData() {
        mRc_view = findViewById(R.id.recycler_view);
        songTv = findViewById(R.id.songTv);
        singerTv = findViewById(R.id.singerTv);
        songButton = findViewById(R.id.songButton);
        singerImg = findViewById(R.id.singerImg);

        mSongTv = songTv;
        mSingerTv = singerTv;
        mSongButton = songButton;
        mSingerImg = singerImg;

        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, MusicService.class);
                intent.putExtra("action", "pause");
                startService(intent);
            }
        });

        //创建数据集合
        mList = new ArrayList<>();

        //创建数据
        for(int i = 0; i < Datas.imgs.length; i++){
            //创建数据对象
            MusicBean bean = new MusicBean();
            bean.setSingerImg(Datas.imgs[i]);
            bean.setSongName(Datas.songs[i]);
            bean.setSingerName(Datas.singers[i]);
            mList.add(bean);
        }

        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRc_view.setLayoutManager(layoutManager);

        //创建适配器
        MyAdapter adapter = new MyAdapter(mList);
        //设置到RecyclerView里面
        mRc_view.setAdapter(adapter);
    }
}