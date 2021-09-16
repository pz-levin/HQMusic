package com.example.hqproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hqproj.beans.Datas;
import com.example.hqproj.beans.MusicBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRc_view;
    private List<MusicBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRc_view = (RecyclerView) findViewById(R.id.recycler_view);
        //准备数据
        initData();
    }

    private void initData() {
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