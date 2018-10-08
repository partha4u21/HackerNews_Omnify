package com.piper.hackernews;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.piper.hackernews.models.Comments;
import com.piper.hackernews.models.TopStories;

import java.util.ArrayList;

/**
 * Created by parthamurmu on 10/09/17.
 */

public class CommentsAdapter extends BaseAdapter {

    private ArrayList<Comments> commentsArrayList;
    private Context context;
    private LayoutInflater inflater;

    public CommentsAdapter(Context context, ArrayList<Comments> commentsList) {
        this.context = context;
        this.commentsArrayList = commentsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CommentsAdapter.Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.comments_item, null);
            holder = new CommentsAdapter.Holder();
            holder.time = (TextView) v.findViewById(R.id.time);
            holder.comment = (TextView) v.findViewById(R.id.comment);
            v.setTag(holder);
        } else {
            holder = (CommentsAdapter.Holder) v.getTag();
        }

        holder.comment.setText(commentsArrayList.get(position).getComment());
        final String time = (String) DateUtils.getRelativeTimeSpanString(Long.parseLong(commentsArrayList.get(position).getTime()) * 1000, System.currentTimeMillis(), 0L);
        holder.time.setText(time);
        return v;
    }

    class Holder {
        TextView time,comment;
    }
}