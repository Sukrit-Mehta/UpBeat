package com.example.sukrit.musicplayer.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sukrit.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by Sukrit on 10/6/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> paths=new ArrayList<String>();
    ArrayList<String> albumNames=new ArrayList<String>();
    LayoutInflater inflater;
    public CustomAdapter(Context context,ArrayList<String> paths , ArrayList<String> albumNames) {
        this.context=context;
        this.paths=paths;
        this.albumNames=albumNames;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.activity_gridview,null);
        ImageView icon= view.findViewById(R.id.img);
        TextView albumName=view.findViewById(R.id.albumName);
        //albumName.setText(albumNames.get(i));
        albumName.setText(albumNames.get(i).substring(albumNames.get(i).indexOf(' ')).trim());
        if(paths.get(i)==null)
        {
            icon.setImageResource(R.drawable.music);
        }
        else {
            icon.setImageURI(Uri.parse(paths.get(i)));
        }
        return view;
    }


}
