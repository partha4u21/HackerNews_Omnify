package com.piper.hackernews.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import java.util.Hashtable;

import com.piper.hackernews.R;

public class FontHelper {

    public static final String TYPEFACE_FOLDER = "fonts";
    public static final String TYPEFACE_EXTENSION = ".ttf";
    public static final String SpinnerFont = "helveticaneuethin";
    public static final String TextThinFont = "helveticaneuethin";
    public static final String TextLightFont = "helveticaneuelight";
    public static final String TextMediumFont = "helveticaneuemed";
    public static final String TextOpenSansFont = "open";
    public static final String TextOpenSansRegularFont = "openreg";
    public static final String TextOpenSansSemiBold = "opensemi";
    public static final String TextUbuntu = "ubuntu";
    public static final String TextUbuntuMed = "ubuntumed";
    public static final String TextDroid = "droid";
    public static final String TextGoodDog = "gooddog";

    private static Hashtable<String, Typeface> sTypeFaces = new Hashtable<String, Typeface>(
            4);

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            tempTypeface = ResourcesCompat.getFont(context, getFont(fileName));
            sTypeFaces.put(fileName, tempTypeface);
        }
        return tempTypeface;
    }

    public static int getFont(String font) {
        if (TextThinFont.contentEquals(font)) {
            return R.font.helveticaneuethin;
        } else if (TextLightFont.contentEquals(font)) {
            return R.font.helveticaneuelight;
        } else if (TextOpenSansFont.contentEquals(font)) {
            return R.font.opensans;
        } else if (TextOpenSansRegularFont.contentEquals(font)) {
            return R.font.opensansregular;
        } else if (TextOpenSansSemiBold.contentEquals(font)) {
            return R.font.opensanssemibold;
        } else if (TextUbuntu.contentEquals(font)) {
            return R.font.ubuntu;
        } else if (TextDroid.contentEquals(font)) {
            return R.font.droid;
        } else if (TextGoodDog.contentEquals(font)) {
            return R.font.gooddog;
        }
        return R.font.helveticaneuemed;
    }
}

