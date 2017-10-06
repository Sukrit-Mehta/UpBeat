package com.example.sukrit.musicplayer.models;

/**
 * Created by Sukrit on 10/6/2017.
 */

public class SongsModel {
    private long mSongId;
    private String mSongTitle;

    public SongsModel() {
    }

    public SongsModel(long mSongId, String mSongTitle) {

        this.mSongId = mSongId;
        this.mSongTitle = mSongTitle;
    }

    public long getmSongId() {
        return mSongId;
    }

    public void setmSongId(long mSongId) {
        this.mSongId = mSongId;
    }

    public String getmSongTitle() {
        return mSongTitle;
    }

    public void setmSongTitle(String mSongTitle) {
        this.mSongTitle = mSongTitle;
    }


}
