package com.example.sukrit.musicplayer.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sukrit.musicplayer.R;
import com.example.sukrit.musicplayer.adapters.CustomAdapter;
import com.example.sukrit.musicplayer.models.SongsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_PERM = 1234;
    ArrayList<SongsModel> songsList;
    ImageView imageView;
    GridView gridView;
    ArrayList<String> pathArray=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //imageView= (ImageView) findViewById(R.id.imageView);

        gridView= (GridView) findViewById(R.id.gridView);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),pathArray);
        gridView.setAdapter(customAdapter);

        songsList=new ArrayList<SongsModel>();
        int permission1= ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if(permission1== PackageManager.PERMISSION_GRANTED)
        {
            YOUR_METHOD_NAME();
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_PERM
            );
        }    }
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
                songsList.add(new SongsModel(currentId, currentTitle));

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
                    pathArray.add(path);
                    // Uri uri=Uri.parse(path);
                    //imageView.setImageURI(uri);
                    Log.d("TTT","paths: "+path);
                }
            } while(songCursor.moveToNext());
        }
//        Uri imageUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//        Cursor albumImageCursor = managedQuery(imageUri,
//                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
//                MediaStore.Audio.Albums._ID+ "=?",
//                new String[] {String.valueOf(8)},
//                null);
//
//        if(albumImageCursor!=null && albumImageCursor.moveToFirst())
//        {
//            String path=albumImageCursor.getString(albumImageCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//            pathArray.add(path);
//           // Uri uri=Uri.parse(path);
//            //imageView.setImageURI(uri);
//            Log.d("TTT","paths: "+path);
//        }
        Log.d("TTT","list: "+songsList.get(0));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        int i;
        if(requestCode==CODE_PERM)
        {
            for(i=0;i<permissions.length;i++)
            {
                if(permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    if(grantResults[i]== PackageManager.PERMISSION_GRANTED)
                    {
                        YOUR_METHOD_NAME();
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
