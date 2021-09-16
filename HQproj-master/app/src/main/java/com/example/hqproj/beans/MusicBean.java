package com.example.hqproj.beans;

public class MusicBean {

    //属性
    private String songName;
    private int singerImg;
    private String singerName;
    private int song;

    //构造
    public MusicBean() {}
    public MusicBean(String songName, int singerImg, String singerName,int song) {
        this.songName = songName;
        this.singerImg = singerImg;
        this.singerName = singerName;
        this.song = song;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSingerImg() {
        return singerImg;
    }

    public void setSingerImg(int singerImg) {
        this.singerImg = singerImg;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "Music{" +
                "songName='" + songName + '\'' +
                ", singerImg='" + singerImg + '\'' +
                ", singerName='" + singerName + '\'' +
                '}';
    }
}
