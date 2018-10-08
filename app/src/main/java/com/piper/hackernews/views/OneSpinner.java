package com.piper.hackernews.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

public class OneSpinner extends AppCompatSpinner {

    public OneSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OneSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneSpinner(Context context) {
        super(context);
        init();
    }

    private void init() {
    }

}