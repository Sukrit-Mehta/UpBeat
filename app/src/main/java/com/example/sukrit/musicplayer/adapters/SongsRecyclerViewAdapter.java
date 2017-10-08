package com.example.sukrit.musicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sukrit.musicplayer.R;
import com.example.sukrit.musicplayer.models.SongsPojo;

import java.util.ArrayList;

/**
 * Created by Sukrit on 10/6/2017.
 */

public class SongsRecyclerViewAdapter extends RecyclerView.Adapter<SongsRecyclerViewAdapter.DetailsItemHolder> {

    ArrayList<SongsPojo> songsDetails;
    Context context;

    public SongsRecyclerViewAdapter(ArrayList<SongsPojo> songsDetails,Context context) {
        this.songsDetails=songsDetails;
        this.context=context;
    }


    @Override
    public DetailsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutType;
        layoutType= R.layout.list_item_song_details;
        View viewItem = li.inflate(layoutType,parent,false);
        return new DetailsItemHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(DetailsItemHolder holder, int position) {
        SongsPojo thisDetail=songsDetails.get(position);
        holder.songName.setText(thisDetail.getTitle());
        holder.artistName.setText(thisDetail.getArtist());
        holder.duration.setText(thisDetail.getMinlength());
    }

    @Override
    public int getItemCount() {
        return songsDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class DetailsItemHolder extends RecyclerView.ViewHolder{
        TextView songName,artistName,duration;
        View testView;

        public DetailsItemHolder(View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.songName);
            artistName=itemView.findViewById(R.id.artistName);
            testView=itemView;
        }
    }
}
