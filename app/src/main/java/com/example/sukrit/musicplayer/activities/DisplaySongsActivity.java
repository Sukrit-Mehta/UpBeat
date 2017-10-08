package com.example.sukrit.musicplayer.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.sukrit.musicplayer.R;
import com.example.sukrit.musicplayer.models.SongsPojo;

import java.util.ArrayList;

public class DisplaySongsActivity extends AppCompatActivity {

    TextView textID;
    ArrayList<SongsPojo> songsArray = new ArrayList<SongsPojo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);

        textID= (TextView) findViewById(R.id.textID);
        Intent myIntent= getIntent();
        Long val = myIntent.getLongExtra("albumID",0);
        textID.setText(String.valueOf(val));
        YOUR_METHOD_NAME();
    }



        public void YOUR_METHOD_NAME(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Audio.Media.IS_MUSIC +"!=0";
        String sortOrder=MediaStore.Audio.Media.TITLE +" ASC";

        Cursor songCursor = contentResolver.query(songUri, null, selection, null, sortOrder);

        if(songCursor != null && songCursor.moveToFirst())
        {
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int albumId=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
           // Toast.makeText(this, "_ID: "+String.valueOf(songId), Toast.LENGTH_SHORT).show();

            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                long currentAlbumId=songCursor.getLong(albumId);
                //songsArray.add(new SongsPojo(currentId, currentTitle));

                Log.d("TTT","currentID: "+String.valueOf(currentId));
                Log.d("TTT","currentTitle: "+String.valueOf(currentTitle));
                Log.d("TTT","currentAlbumId: "+String.valueOf(currentAlbumId));

                Uri imageUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
                Cursor albumImageCursor = managedQuery(imageUri,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(currentAlbumId)},
                        null);

                if(albumImageCursor!=null && albumImageCursor.moveToFirst())
                {
                    String path=albumImageCursor.getString(albumImageCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                   // pathArray.add(path);
                    // Uri uri=Uri.parse(path);
                    //imageView.setImageURI(uri);
                    Log.d("TTT","paths: "+path);
                }
            } while(songCursor.moveToNext());
        }

       // Log.d("TTT","list: "+songsList.get(0));
    }

}
