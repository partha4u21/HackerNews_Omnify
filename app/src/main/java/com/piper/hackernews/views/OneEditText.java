package com.piper.hackernews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.piper.hackernews.helper.FontHelper;

import com.piper.hackernews.R;
import com.piper.hackernews.helper.FontHelper;

public class OneEditText extends AppCompatEditText {

    public OneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.OneEditText, 0, 0);
        String font = a.getString(R.styleable.OneEditText_editStyle);
        a.recycle();
        init(font);
    }

    public OneEditText(Context context) {
        super(context);
    }

    private void init(String font) {
        if (!isInEditMode()) {
            if (font != null) {
                setTypeface(FontHelper.getTypeFace(getContext(), font));
            }
        } else {
            setTypeface(FontHelper.getTypeFace(getContext(), FontHelper.TextMediumFont));
        }
    }
}
