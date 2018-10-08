package com.piper.hackernews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.piper.hackernews.R;
import com.piper.hackernews.helper.FontHelper;

public class OneText extends AppCompatTextView {


    public OneText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.OneText, 0, 0);
        String font = a.getString(R.styleable.OneText_textStyle);
        a.recycle();
        init(font);
    }

    public OneText(Context context) {
        super(context);
    }

    private void init(String font) {
        if (!isInEditMode()) {
            if (font != null) {
                setTypeface(FontHelper.getTypeFace(getContext(), font));
            } else {
                setTypeface(FontHelper.getTypeFace(getContext(), FontHelper.TextMediumFont));
            }
        }
    }
}
