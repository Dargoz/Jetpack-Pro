package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;

public class Image {
    private int id;
    private Bitmap bitmap;

    public Image(int id, Bitmap bitmap) {
        this.id = id;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
