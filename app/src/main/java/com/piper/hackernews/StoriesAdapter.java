package com.piper.hackernews;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.piper.hackernews.models.TopStories;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by parthamurmu on 06/09/17.
 */

public class StoriesAdapter extends BaseAdapter {

    private ArrayList<TopStories> storiesArrayList;
    private Context context;
    private LayoutInflater inflater;

    public StoriesAdapter(Context context, ArrayList<TopStories> storiesArrayList) {
        this.context = context;
        this.storiesArrayList = storiesArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return storiesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return storiesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.stories_item, null);
            holder = new Holder();
            holder.likes = (TextView) v.findViewById(R.id.likes);
            holder.title = (TextView) v.findViewById(R.id.title);
            holder.url = (TextView) v.findViewById(R.id.url);
            holder.time = (TextView) v.findViewById(R.id.time);
            holder.username = (TextView) v.findViewById(R.id.username);
            holder.comment_count = (TextView) v.findViewById(R.id.comment_count);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.likes.setText(storiesArrayList.get(position).getScore());
        holder.title.setText(storiesArrayList.get(position).getTitle());
        holder.url.setText(storiesArrayList.get(position).getUrl());
        final String time = (String) DateUtils.getRelativeTimeSpanString(Long.parseLong(storiesArrayList.get(position).getTime()) * 1000, System.currentTimeMillis(), 0L);
        holder.time.setText(time);
        holder.username.setText(storiesArrayList.get(position).getUsername());
        try {
            int commentCount = new JSONArray(storiesArrayList.get(position).getKids()).length();
            holder.comment_count.setText(String.valueOf(commentCount));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StoriesDetailActivity.class);
                intent.putExtra("title", storiesArrayList.get(position).getTitle());
                intent.putExtra("id", storiesArrayList.get(position).getId());
                intent.putExtra("url", storiesArrayList.get(position).getUrl());
                intent.putExtra("time", time);
                intent.putExtra("username", storiesArrayList.get(position).getUsername());
                intent.putExtra("comments_id", storiesArrayList.get(position).getKids());
                v.getContext().startActivity(intent);
            }
        });
        return v;
    }

    class Holder {
        TextView likes, title, url, time, username, comment_count;
    }
}