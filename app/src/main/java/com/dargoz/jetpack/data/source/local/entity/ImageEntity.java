package com.dargoz.jetpack.data.source.local.entity;

import android.graphics.Bitmap;

public class ImageEntity {
    private int id;
    private Bitmap bitmap;

    public ImageEntity(int id, Bitmap bitmap) {
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
