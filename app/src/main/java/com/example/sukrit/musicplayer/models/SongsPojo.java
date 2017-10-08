package com.example.sukrit.musicplayer.models;

/**
 * Created by Manoj on 7/24/2017.
 */

public class SongsPojo {

    String Title,Artist,Albumname, seclength;
    String minlength;


    public SongsPojo(String title, String artist, String albumname,
                     String min, String sec) {
        this.Title = title;
        this.Artist = artist;
        this.Albumname = albumname;
        this.minlength =min;
        this.seclength = sec;
    }


    public String getMinlength() {
        return minlength;
    }

    public void setMinlength(String minlength) {
        this.minlength = minlength;
    }

    public String getSeclength() {
        return seclength;
    }

    public void setSeclength(String seclength) {
        this.seclength = seclength;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbumname() {
        return Albumname;
    }

    public void setAlbumname(String albumname) {
        Albumname = albumname;
    }


}
