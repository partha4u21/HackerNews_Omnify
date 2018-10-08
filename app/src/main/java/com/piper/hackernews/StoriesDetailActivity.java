package com.piper.hackernews;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Vector;

/**
 * Created by parthamurmu on 09/09/17.
 */

public class StoriesDetailActivity extends FragmentActivity implements ShareDataInterface {

    TextView title, url, time, userName;
    JSONArray comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        title = findViewById(R.id.title);
        url = findViewById(R.id.url);
        time = findViewById(R.id.time);
        userName = findViewById(R.id.username);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        title.setText(getIntent().getStringExtra("title"));
        url.setText(getIntent().getStringExtra("url"));
        time.setText(getIntent().getStringExtra("time"));
        userName.setText(getIntent().getStringExtra("username"));
        try {
            comments = new JSONArray(getIntent().getStringExtra("comments_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getID() {
        return getIntent().getStringExtra("id");
    }

    @Override
    public String getUrl() {
        return getIntent().getStringExtra("url");
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return new CommentsDetailFragment();
                case 1:
                    return new ArticleDetailFragment().newInstance(getUrl());
                default:
                    return new CommentsDetailFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1) {
                return "Article";
            }
            return "Comments";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}