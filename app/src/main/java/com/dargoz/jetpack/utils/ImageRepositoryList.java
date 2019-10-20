package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ImageRepositoryList {
    private static ArrayList<Image> imageArrayList = new ArrayList<>();

    public static void addImage(Image image){
        imageArrayList.add(image);
    }

    public static Bitmap findImage(int id){
        for(Image image : imageArrayList){
            if(image.getId() == id){
                return image.getBitmap();
            }
        }
        return null;
    }
}
