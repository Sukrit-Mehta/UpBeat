package com.example.sukrit.musicplayer.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sukrit.musicplayer.R;
import com.example.sukrit.musicplayer.adapters.CustomAdapter;
import com.example.sukrit.musicplayer.models.SongsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_PERM = 1234;
    ArrayList<SongsModel> songsList;
    GridView gridView;
    ArrayList<String> pathArray=new ArrayList<String>();
    ArrayList<String> albumNames=new ArrayList<String>();
    ArrayList<Long> albumIDs=new ArrayList<Long>();

    int i;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //imageView= (ImageView) findViewById(R.id.imageView);

        editText= (EditText) findViewById(R.id.editText);

        gridView= (GridView) findViewById(R.id.gridView);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),pathArray,albumNames);
        gridView.setAdapter(customAdapter);

        songsList=new ArrayList<SongsModel>();
        int permission1= ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if(permission1== PackageManager.PERMISSION_GRANTED)
        {
            getAlbumList();
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_PERM
            );
        }
       // Toast.makeText(this, "Path Array: "+pathArray.toString(), Toast.LENGTH_SHORT).show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, albumIDs.get(i)+ " clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,DisplaySongsActivity.class);
                intent.putExtra("albumID",albumIDs.get(i));
                startActivity(intent);
            }
        });
      /*  editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<String> temp=new ArrayList<String>();
                Log.d("HHH","Size"+albumNames.size());
                for(i=0;i<albumNames.size();i++)
                {
                    if(albumNames.get(i).contains(charSequence))
                    {
                        temp.add(albumNames.get(i));
                    }
                }
                CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),pathArray,temp);
                gridView.setAdapter(customAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

//    public void YOUR_METHOD_NAME(){
//        ContentResolver contentResolver = getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection=MediaStore.Audio.Media.IS_MUSIC +"!=0";
//        String sortOrder=MediaStore.Audio.Media.TITLE +" ASC";
//        Cursor songCursor = contentResolver.query(songUri, null, selection, null, sortOrder);
//
//        if(songCursor != null && songCursor.moveToFirst())
//        {
//            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
//            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int albumId=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
//           // Toast.makeText(this, "_ID: "+String.valueOf(songId), Toast.LENGTH_SHORT).show();
//
//            do {
//                long currentId = songCursor.getLong(songId);
//                String currentTitle = songCursor.getString(songTitle);
//                long currentAlbumId=songCursor.getLong(albumId);
//                songsList.add(new SongsModel(currentId, currentTitle));
//
//                Log.d("TTT","currentID: "+String.valueOf(currentId));
//                Log.d("TTT","currentTitle: "+String.valueOf(currentTitle));
//                Log.d("TTT","currentAlbumId: "+String.valueOf(currentAlbumId));
//
//                Uri imageUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//                Cursor albumImageCursor = managedQuery(imageUri,
//                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
//                        MediaStore.Audio.Albums._ID+ "=?",
//                        new String[] {String.valueOf(currentAlbumId)},
//                        null);
//
//                if(albumImageCursor!=null && albumImageCursor.moveToFirst())
//                {
//                    String path=albumImageCursor.getString(albumImageCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//                    pathArray.add(path);
//                    // Uri uri=Uri.parse(path);
//                    //imageView.setImageURI(uri);
//                    Log.d("TTT","paths: "+path);
//                }
//            } while(songCursor.moveToNext());
//        }
//
//        Log.d("TTT","list: "+songsList.get(0));
//    }

//    public void YOUR_METHOD_NAME(){
//        ContentResolver contentResolver = getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String[] projection={MediaStore.Audio.Albums.ALBUM_ART,"DISTINCT "+
//        MediaStore.Audio.Albums.ALBUM_ID};
//        String sortOrder=MediaStore.Audio.Albums.NUMBER_OF_SONGS +" DESC";
//        Cursor songCursor = contentResolver.query(songUri, projection, null, null, null);
//
//        if(songCursor != null && songCursor.moveToFirst())
//        {
////            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
////            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int albumId=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
//            // Toast.makeText(this, "_ID: "+String.valueOf(songId), Toast.LENGTH_SHORT).show();
//
//            do {
////                long currentId = songCursor.getLong(songId);
////                String currentTitle = songCursor.getString(songTitle);
//                long currentAlbumId=songCursor.getLong(albumId);
//                //songsList.add(new SongsModel(currentId, currentTitle));
//
////                Log.d("TTT","currentID: "+String.valueOf(currentId));
////                Log.d("TTT","currentTitle: "+String.valueOf(currentTitle));
//                Log.d("TTT","currentAlbumId: "+String.valueOf(currentAlbumId));
//
//                Uri imageUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//                Cursor albumImageCursor = getContentResolver().query(imageUri,
//                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
//                        MediaStore.Audio.Albums._ID + "=?",
//                        new String[]{String.valueOf(currentAlbumId)},
//                        null);
//
//                if(albumImageCursor!=null && albumImageCursor.moveToFirst())
//                {
//                    String path=albumImageCursor.getString(albumImageCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//                    pathArray.add(path);
//                    // Uri uri=Uri.parse(path);
//                    //imageView.setImageURI(uri);
//                    Log.d("TTT","paths: "+path);
//                }
//            } while(songCursor.moveToNext());
//        }
//
//    }
public void getAlbumList(){
    String[] projection = new String[] { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.NUMBER_OF_SONGS };
    String selection = null;
    String[] selectionArgs = null;
    String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
    Cursor cursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);

    if(cursor!=null &&cursor.moveToFirst())
    {
        int albIdCol=cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
        int albArtCol=cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
        int nameCol=cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        do{
            long albId=cursor.getLong(albIdCol);
            String path=cursor.getString(albArtCol);
            String nameAlbum=cursor.getString(nameCol);
            pathArray.add(path);
            albumNames.add(nameAlbum);
            albumIDs.add(albId);
            Log.d("TTT","albId: "+String.valueOf(albId));
            Log.d("TTT","albArt: "+path);
            Log.d("TTT","nameAlbum: "+nameAlbum);
        }while (cursor.moveToNext());
    }
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
                        getAlbumList();
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
