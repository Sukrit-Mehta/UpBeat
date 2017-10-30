package com.example.sukrit.musicplayer.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sukrit.musicplayer.R;
import com.example.sukrit.musicplayer.adapters.EachSongDetailRecyclerViewAdapter;
import com.example.sukrit.musicplayer.models.SongsPojo;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class DisplaySongsActivity extends AppCompatActivity {

    TextView textID;
    ArrayList<SongsPojo> songsArray = new ArrayList<SongsPojo>();
    Long val;
    RecyclerView recyclerView;
    MaterialSearchView searchView;
    ArrayList<String> songsNames=new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_songs);

        Toolbar toolbar= (Toolbar) findViewById(R.id.appBarLayoutAllSongs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Search View
        searchView= (MaterialSearchView) findViewById(R.id.search_view);

        //textID= (TextView) findViewById(R.id.textID);
        Intent myIntent= getIntent();
        val = myIntent.getLongExtra("albumID",0)+1;
        //textID.setText(String.valueOf(val));
        YOUR_METHOD_NAME();
        recyclerView= (RecyclerView) findViewById(R.id.displaySongsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplaySongsActivity.this));
        recyclerView.setAdapter(new EachSongDetailRecyclerViewAdapter(songsArray,DisplaySongsActivity.this));

        for(int i=0;i<songsArray.size();i++)
        {
            songsNames.add(songsArray.get(i).getTitle());
        }
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recyclerView.setLayoutManager(new LinearLayoutManager(DisplaySongsActivity.this));
                recyclerView.setAdapter(new EachSongDetailRecyclerViewAdapter(songsArray,DisplaySongsActivity.this));

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty() && songsArray.size()>0)
                {
                    ArrayList<SongsPojo> filteredArrayList=new ArrayList<SongsPojo>();
                    ArrayList<String> strArray=new ArrayList<String>();

                    Log.d("LSS","arr: "+songsNames.toString());
                    for(int i=0;i<songsArray.size();i++)
                    {
                        if(songsArray.get(i).getTitle().toLowerCase().contains(newText.toLowerCase()))
                        {
//                            filteredArrayList.get(i).setTitle(songsArray.get(i).setTitle(newText));
//                            filteredArrayList.add(new SongsPojo(songsArray))
                              strArray.add(songsArray.get(i).getTitle());
                            filteredArrayList.add(new SongsPojo(songsArray.get(i).getTitle(),songsArray.get(i).getArtist(),songsArray.get(i).getAlbumname()
                            ,songsArray.get(i).getMinlength(),songsArray.get(i).getSeclength()));
                            Log.d("DSS","Fil: "+strArray.toString());
                            Log.d("DSS","Fil: "+filteredArrayList.toString());
                        }
                    }
                    recyclerView.setAdapter(new EachSongDetailRecyclerViewAdapter(filteredArrayList,DisplaySongsActivity.this));
                }
                else
                {
                    recyclerView.setAdapter(new EachSongDetailRecyclerViewAdapter(songsArray,DisplaySongsActivity.this));
                }
                return true;
            }
        });
    }

        public void YOUR_METHOD_NAME(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Audio.Media.IS_MUSIC +"!=0 "+" AND "+ MediaStore.Audio.Media.ALBUM_ID+" = "+String.valueOf(val-1) ;
       //     String selection=MediaStore.Audio.Media.IS_MUSIC +"!=0 ";
        String sortOrder=MediaStore.Audio.Media._ID +" ASC";
            String print=MediaStore.Audio.Media._ID+"="+String.valueOf(val);
                    Log.d("RRR","print : "+print);

        Cursor songCursor = contentResolver.query(songUri, null, selection, null, sortOrder);

            Log.d("RRR","cursor: "+songCursor);
        if(songCursor != null && songCursor.moveToFirst())
        {
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int albumId=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int artistID=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int durationID=songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int albumNameID=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

//            Log.d("RRRR","songId: "+String.valueOf(songId));
//            Log.d("RRRR","songTitle: "+String.valueOf(songTitle));
//            Log.d("RRRR","albumId: "+String.valueOf(albumId));
           // Toast.makeText(this, "_ID: "+String.valueOf(songId), Toast.LENGTH_SHORT).show();

            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                long currentAlbumId=songCursor.getLong(albumId)+1;
                String artistName=songCursor.getString(artistID);
                Long duration=songCursor.getLong(durationID);
                String albumName=songCursor.getString(albumNameID);
                String albumNameFinal=albumName.substring(albumName.indexOf(' ')).trim();

                //songsArray.add(new SongsPojo(currentId, currentTitle));

                Log.d("RRR","currentID: "+String.valueOf(currentId));
                Log.d("RRR","currentTitle: "+String.valueOf(currentTitle));
                Log.d("RRR","currentAlbumId: "+String.valueOf(currentAlbumId));
                Log.d("RRR","artistName: "+artistName);
                Log.d("RRR","duration: "+String.valueOf(duration));
                Log.d("RRR","albumName: " +albumName.substring(albumName.indexOf(' ')).trim());
                Long min=(duration/100)/60;
                Long sec=(duration/100)%60;

                songsArray.add(new SongsPojo(currentTitle,artistName,albumNameFinal,String.valueOf(min),String.valueOf(sec)));

                Uri imageUri=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
                Cursor albumImageCursor = managedQuery(imageUri,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART,
                                MediaStore.Audio.Albums.ARTIST,MediaStore.Audio.Albums.NUMBER_OF_SONGS},
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

//=======================================================================================================================================//
/*
10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 2
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 01. Dilwale  (2015)
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 10
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 016  dangal
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 11
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 017  Befikre (2016) (128 Kbps)
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 14
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 018 Dear Zindagi (2016) (128 Kbps)
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 3
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 02. Bajirao Mastani
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 4
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.457 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 03. Hate Story 3
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 5
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 06. Prem Ratan Dhan Payo
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 6
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 16. Hero (2015)
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 7
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 18. Welcome Back
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 8
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: /storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1507231388682
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 19. Bajrangi Bhaijaan (2015)
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 9
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: /storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1507231393270
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: 23. Tanu Weds Manu Returns (2015)
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 15
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: /storage/emulated/0/Android/data/com.android.providers.media/albumthumbs/1507285498574
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: Sohniye - Mika & Daler (2016)
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albId: 1
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: albArt: null
        10-09 01:51:02.458 19071-19071/com.example.sukrit.musicplayer D/TTT: nameAlbum: WhatsApp Audio
*/
/*
* 10-09 01:51:56.528 19071-19071/com.example.sukrit.musicplayer D/RRR: print : _id=10
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRR: cursor: android.content.ContentResolver$CursorWrapperInner@af28fff
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRRR: songId: 0
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRRR: songTitle: 8
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRRR: albumId: 13
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5766
10-09 01:51:56.538 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Gajanana
10-09 01:51:56.539 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.547 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5758
10-09 01:51:56.547 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Gerua
10-09 01:51:56.547 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:56.553 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5837
10-09 01:51:56.553 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Haanikaarak Bapu - DownloadMing.SE
10-09 01:51:56.553 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.561 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5866
10-09 01:51:56.561 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Just Go to Hell Dil - DownloadMing.SE
10-09 01:51:56.561 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:56.567 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5845
10-09 01:51:56.567 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Labo Kaa Kalobar (Befikre) - DownloadMing.SE
10-09 01:51:56.567 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:56.574 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5794
10-09 01:51:56.574 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Main Hoon Hero Tera
10-09 01:51:56.574 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:56.583 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5783
10-09 01:51:56.583 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Prem Leela
10-09 01:51:56.584 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:56.594 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5814
10-09 01:51:56.594 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Selfie Le Le Re
10-09 01:51:56.594 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:56.601 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5777
10-09 01:51:56.601 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Tumhe Apna Banane Ka
10-09 01:51:56.601 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 5
10-09 01:51:56.609 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5803
10-09 01:51:56.609 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 - Tutti Bole Wedding Di
10-09 01:51:56.609 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:56.616 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5826
10-09 01:51:56.616 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 01 Banno
10-09 01:51:56.616 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:56.623 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5767
10-09 01:51:56.623 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 -  Deewani Mastani
10-09 01:51:56.623 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.631 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5804
10-09 01:51:56.631 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - 20-20
10-09 01:51:56.632 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:56.639 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5815
10-09 01:51:56.640 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Aaj Ki Party  -  Bajrangi Bhaijaan
10-09 01:51:56.640 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:56.648 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5838
10-09 01:51:56.648 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Dhaakad - DownloadMing.SE
10-09 01:51:56.648 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.657 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5867
10-09 01:51:56.657 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Luv u Zindaggi - DownloadMing.SE
10-09 01:51:56.657 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:56.666 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5759
10-09 01:51:56.666 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Manma Emotion Jaage
10-09 01:51:56.666 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:56.674 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5846
10-09 01:51:56.674 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Nashe Si Chadh Gayi - DownloadMing.SE
10-09 01:51:56.674 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:56.681 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5784
10-09 01:51:56.681 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Prem Ratan Dhan Payo
10-09 01:51:56.681 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:56.688 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5778
10-09 01:51:56.688 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Tu Isaq Mera
10-09 01:51:56.688 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 5
10-09 01:51:56.698 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5795
10-09 01:51:56.698 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 - Yadaan Teriyaan
10-09 01:51:56.698 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:56.705 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5827
10-09 01:51:56.705 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 02 Move On
10-09 01:51:56.705 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:56.713 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5796
10-09 01:51:56.713 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Dance Ke Legend
10-09 01:51:56.713 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:56.720 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5839
10-09 01:51:56.720 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Gilehriyaan - DownloadMing.SE
10-09 01:51:56.720 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.728 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5785
10-09 01:51:56.728 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Jalte Diye
10-09 01:51:56.728 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:56.737 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5760
10-09 01:51:56.737 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Janam Janam
10-09 01:51:56.737 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:56.745 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5768
10-09 01:51:56.745 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Pinga
10-09 01:51:56.745 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.752 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5816
10-09 01:51:56.752 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Tu Chahiye
10-09 01:51:56.752 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:56.760 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5868
10-09 01:51:56.760 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Tu Hi Hai - DownloadMing.SE
10-09 01:51:56.760 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:56.769 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5847
10-09 01:51:56.769 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Ude Dil Befikre (Befikre) - DownloadMing.SE
10-09 01:51:56.769 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:56.777 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5779
10-09 01:51:56.778 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Wajah Tum Ho
10-09 01:51:56.778 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 5
10-09 01:51:56.785 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5805
10-09 01:51:56.785 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 - Welcome Back
10-09 01:51:56.785 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:56.793 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5828
10-09 01:51:56.793 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 03 Mat Ja Re
10-09 01:51:56.793 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:56.801 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5786
10-09 01:51:56.801 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Aaj Unse Milna Hai
10-09 01:51:56.801 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:56.810 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5817
10-09 01:51:56.810 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Bhar Do Jholi Meri
10-09 01:51:56.810 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:56.817 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5840
10-09 01:51:56.817 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Dangal - Title Song - DownloadMing.SE
10-09 01:51:56.817 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.825 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5797
10-09 01:51:56.825 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Khoya Khoya
10-09 01:51:56.825 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:56.833 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5806
10-09 01:51:56.833 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Meet Me Daily Baby
10-09 01:51:56.833 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:56.841 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5769
10-09 01:51:56.841 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Mohe Rang Do Laal
10-09 01:51:56.841 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.849 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5780
10-09 01:51:56.849 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Neendein Khul Jaati Hain
10-09 01:51:56.849 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 5
10-09 01:51:56.857 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5869
10-09 01:51:56.857 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Taarefon Se - DownloadMing.SE
10-09 01:51:56.857 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:56.865 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5761
10-09 01:51:56.865 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - Tukur Tukur
10-09 01:51:56.865 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:56.874 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5848
10-09 01:51:56.874 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 - You and Me - DownloadMing.SE
10-09 01:51:56.874 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:56.882 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5829
10-09 01:51:56.882 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 04 Ghani Bawri
10-09 01:51:56.882 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:56.890 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5770
10-09 01:51:56.890 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Albela Sajan
10-09 01:51:56.890 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.897 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5818
10-09 01:51:56.898 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Chicken Song
10-09 01:51:56.898 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:56.905 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5762
10-09 01:51:56.905 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Daayre
10-09 01:51:56.905 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:56.914 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5787
10-09 01:51:56.914 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Jab Tum Chaho
10-09 01:51:56.914 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:56.922 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5849
10-09 01:51:56.922 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Khulke Dulke (Befikre) - DownloadMing.SE
10-09 01:51:56.922 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:56.930 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5870
10-09 01:51:56.930 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Lets Break Up - DownloadMing.SE
10-09 01:51:56.930 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:56.939 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5781
10-09 01:51:56.939 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Love To Hate you
10-09 01:51:56.939 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 5
10-09 01:51:56.948 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5841
10-09 01:51:56.948 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Naina - DownloadMing.SE
10-09 01:51:56.948 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.956 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5798
10-09 01:51:56.956 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - O Khuda
10-09 01:51:56.956 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:56.964 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5807
10-09 01:51:56.964 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 - Time Lagaye Kaiko
10-09 01:51:56.964 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:56.972 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5830
10-09 01:51:56.972 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 05 Old School Girl
10-09 01:51:56.972 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:56.980 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5771
10-09 01:51:56.980 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Ab Tohe Jane Na Doongi
10-09 01:51:56.980 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:56.988 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5842
10-09 01:51:56.988 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Dhaakad (Aamir Khan Version) - DownloadMing.SE
10-09 01:51:56.988 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:56.996 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5788
10-09 01:51:56.996 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Halo Re
10-09 01:51:56.996 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:57.003 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5799
10-09 01:51:57.003 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Jab We Met
10-09 01:51:57.003 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:57.011 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5850
10-09 01:51:57.011 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Je T aime - DownloadMing.SE
10-09 01:51:57.011 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:57.019 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5871
10-09 01:51:57.019 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Love You Zindagi (Club Mix) - DownloadMing.SE
10-09 01:51:57.019 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:57.027 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5808
10-09 01:51:57.027 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Nas Nas Mein
10-09 01:51:57.027 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:57.035 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5763
10-09 01:51:57.035 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Premika
10-09 01:51:57.036 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:57.045 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5819
10-09 01:51:57.045 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 - Tu Jo Mila - K K
10-09 01:51:57.045 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.053 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5831
10-09 01:51:57.053 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 06 Old School Girl
10-09 01:51:57.053 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:57.060 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5772
10-09 01:51:57.060 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Aayat
10-09 01:51:57.060 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:57.066 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5872
10-09 01:51:57.066 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Ae Zindagi Gale Laga Le (Take 1) - DownloadMing.SE
10-09 01:51:57.067 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:57.074 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5809
10-09 01:51:57.074 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Damaa Dam Mast Kalandar
10-09 01:51:57.075 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:57.082 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5843
10-09 01:51:57.082 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Idiot Banna - DownloadMing.SE
10-09 01:51:57.083 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 11
10-09 01:51:57.091 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5851
10-09 01:51:57.091 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Love Is a Dare (Instrumental) - DownloadMing.SE
10-09 01:51:57.091 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 12
10-09 01:51:57.097 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5764
10-09 01:51:57.097 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Theme of Dilwale (DJ Chetas Mix)
10-09 01:51:57.097 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 3
10-09 01:51:57.105 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5789
10-09 01:51:57.105 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Tod Tadaiyya
10-09 01:51:57.105 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:57.112 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5800
10-09 01:51:57.112 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Yadaan Teriyaan (Version 2)
10-09 01:51:57.113 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:57.120 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5820
10-09 01:51:57.120 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 - Zindagi (Reprise)
10-09 01:51:57.120 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.127 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5832
10-09 01:51:57.127 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 07 Ho Gaya Hai Pyar
10-09 01:51:57.127 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:57.134 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5773
10-09 01:51:57.134 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Aaj Ibaadat
10-09 01:51:57.134 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:57.142 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5873
10-09 01:51:57.142 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Ae Zindagi Gale Laga Le (Take 2) - DownloadMing.SE
10-09 01:51:57.142 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 15
10-09 01:51:57.150 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5790
10-09 01:51:57.150 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Bachpan Kahan
10-09 01:51:57.150 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:57.159 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5821
10-09 01:51:57.159 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Bhar Do Jholi Meri (Reprise)
10-09 01:51:57.159 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.167 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5801
10-09 01:51:57.167 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Main Hoon Hero Tera (Armaan Malik
10-09 01:51:57.167 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 7
10-09 01:51:57.176 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5810
10-09 01:51:57.176 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 - Welcome Back (Beat Mix)
10-09 01:51:57.176 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:57.183 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5833
10-09 01:51:57.183 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 08 O Sathi Mere
10-09 01:51:57.183 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:57.190 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5774
10-09 01:51:57.190 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 09 - Fitoori
10-09 01:51:57.190 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:57.197 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5811
10-09 01:51:57.197 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 09 - Meet Me Daily Baby (Beat Mix)
10-09 01:51:57.197 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:57.205 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5791
10-09 01:51:57.205 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 09 - Murli Ki Taanon Si
10-09 01:51:57.205 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:57.212 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5822
10-09 01:51:57.212 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 09 - Tu Jo Mila
10-09 01:51:57.212 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.221 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5834
10-09 01:51:57.221 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 09 Ghani Bawri - Remix
10-09 01:51:57.221 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 10
10-09 01:51:57.227 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5792
10-09 01:51:57.227 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 10 - Aaj Unse Kehna Hai
10-09 01:51:57.227 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 6
10-09 01:51:57.234 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5775
10-09 01:51:57.234 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 10 - Malhari
10-09 01:51:57.234 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 4
10-09 01:51:57.240 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5812
10-09 01:51:57.240 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 10 - Welcome Back (Theme)
10-09 01:51:57.240 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 8
10-09 01:51:57.247 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5823
10-09 01:51:57.247 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 10 - Zindagi Kuch Toh Bata (Reprise)
10-09 01:51:57.247 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.253 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5824
10-09 01:51:57.253 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: 11 - Tu Jo Mila (Reprise)
10-09 01:51:57.253 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 9
10-09 01:51:57.260 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 3307
10-09 01:51:57.260 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: AUD-20170726-WA0005
10-09 01:51:57.260 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 2
10-09 01:51:57.267 19071-19071/com.example.sukrit.musicplayer D/RRR: currentID: 5875
10-09 01:51:57.267 19071-19071/com.example.sukrit.musicplayer D/RRR: currentTitle: Sohniye (128 Kbps) -  DownloadMing.SE
10-09 01:51:57.267 19071-19071/com.example.sukrit.musicplayer D/RRR: currentAlbumId: 16
*/