package com.dargoz.jetpack.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.dargoz.jetpack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static Application application;
    public static void init(Application application){
        Utils.application = application;
    }

    public static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static String formatDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = dateFormat.parse(dateString);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String formatRuntime(int runtime) {
        return String.format(Locale.getDefault(), "%dh %dm", runtime / 60, runtime % 60);
    }

    public static String getObjectImageUrl(String url, String imageSize, String path) {
        return url + imageSize + path;
    }

    public static Bitmap getBitmapFromDrawable(int drawableResource){
        return BitmapFactory.decodeResource(application.getResources(), drawableResource);
    }
}
