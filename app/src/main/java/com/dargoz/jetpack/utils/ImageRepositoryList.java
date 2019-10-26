package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;

import com.dargoz.jetpack.data.source.local.entity.ImageEntity;

import java.util.ArrayList;

public class ImageRepositoryList {
    private static ArrayList<ImageEntity> imageEntityArrayList = new ArrayList<>();

    public static void addImage(ImageEntity imageEntity){
        imageEntityArrayList.add(imageEntity);
    }

    public static Bitmap findImage(int id){
        for(ImageEntity imageEntity : imageEntityArrayList){
            if(imageEntity.getId() == id){
                return imageEntity.getBitmap();
            }
        }
        return null;
    }
}
