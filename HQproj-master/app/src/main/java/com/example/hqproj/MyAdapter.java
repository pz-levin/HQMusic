package com.example.hqproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hqproj.beans.Datas;
import com.example.hqproj.beans.MusicBean;

import java.util.List;
import java.util.concurrent.BlockingDeque;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.InnerHolder> {

    public static int mPosition;
    private List<MusicBean> mList;

    public MyAdapter(List<MusicBean> list) {
        this.mList = list;
    }

    /**
     * 用于创建条目的view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public InnerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context mContext = parent.getContext();

        //传进去的view就是条目的界面
        //两个步骤：1.拿到view  2.创建innerHolder
        View view = View.inflate(parent.getContext(), R.layout.item, null);

        //点击事件
        final InnerHolder holder = new InnerHolder(view);
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MusicBean music = mList.get(position);
                Toast.makeText(view.getContext(), "你弹了一下" + music.getSingerName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Log.d("xxxxxxxxx", "position --->" +position + "");
                mPosition = position;
                Log.d("xxxxxxxxx", "mPosition --->"+ mPosition + "");
                Intent intent = new Intent(mContext,MusicActivity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    /**
     * 用于绑定holder的，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(InnerHolder holder, int position) {
        //在这里设置数据
        holder.setData(mList.get(position));
    }

    /**
     * 返回条目个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private View mSongView;
        private ImageView mImg;
        private TextView mSongName;
        private TextView mSingerName;

        public InnerHolder(View view) {
            super(view);
            //找到条目的控件
            mSongView = view;
            mImg = itemView.findViewById(R.id.rv_img);
            mSongName = itemView.findViewById(R.id.listView_song);
            mSingerName = itemView.findViewById(R.id.listView_singer);
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(MusicBean musicBean) {
            //开始设置数据
            mImg.setImageResource(musicBean.getSingerImg());
            mSingerName.setText(musicBean.getSingerName());
            mSongName.setText(musicBean.getSongName());
        }
    }
}
