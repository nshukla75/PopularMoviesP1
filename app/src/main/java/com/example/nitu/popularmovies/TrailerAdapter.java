package com.example.nitu.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nitus on 10/9/2015.
 */
public class TrailerAdapter extends BaseAdapter {
    private final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    private static final String KEY_ADAPTER_STATE = "TrailerAdapter.KEY_ADAPTER_STATE";
    private final Context context;
    private final List<TrailerData> trailers = new ArrayList<>();

    public TrailerAdapter(Context context) {
        this.context = context;
    }

    public void onSaveInstanceState(Bundle outState) {
             outState.putParcelableArrayList(KEY_ADAPTER_STATE, getAllItems());
           }

    public ArrayList<TrailerData> getAllItems(){
        ArrayList<TrailerData> objects = new ArrayList<TrailerData>(getCount());
        for (int i = 0; i < getCount(); i++) {
            objects.add(getItem(i));
        }
        return objects;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(KEY_ADAPTER_STATE)) {
            ArrayList<TrailerData> objects = savedInstanceState
                    .getParcelableArrayList(KEY_ADAPTER_STATE);
            setItems(objects);
        }
    }

    public void setItems(ArrayList<TrailerData> items){
        this.trailers.clear();
        this.trailers.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new VideoView(context);
        }
        VideoView videoView = (VideoView) convertView;
        TrailerData trailer = getItem(position);
        String url ="https://www.youtube.com/watch?v="+ trailer.getTrailerKey();
        Log.v(LOG_TAG, " URL " + url);
        videoView.setVideoURI(Uri.parse(url));
        return convertView;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public TrailerData getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replace(List<TrailerData> trailersData) {
        this.trailers.clear();
        this.trailers.addAll(trailersData);
        notifyDataSetChanged();
    }
}
