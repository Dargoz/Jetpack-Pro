package com.dargoz.jetpack.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;

import java.util.ArrayList;


public class DummyData {
    private static String[] dataMovieTitle;
    private static String[] dataMovieReleaseDate;
    private static String[] dataMovieDesc;
    private static String[] dataMovieGenre;
    private static String[] dataMovieStatus;
    private static String[] dataMovieScore;
    private static String[] dataMovieRuntime;
    private static TypedArray dataPoster;

    private static void prepareData(Context context){
        dataMovieTitle = context
                .getResources().getStringArray(R.array.movie_title);
        dataMovieReleaseDate = context
                .getResources().getStringArray(R.array.movie_release_date);
        dataMovieDesc = context
                .getResources().getStringArray(R.array.movie_desc);
        dataMovieGenre = context
                .getResources().getStringArray(R.array.movie_genre);
        dataMovieStatus =context
                .getResources().getStringArray(R.array.movie_release_status);
        dataMovieScore = context
                .getResources().getStringArray(R.array.movie_score);
        dataMovieRuntime = context
                .getResources().getStringArray(R.array.movie_runtime);
        dataPoster = context
                .getResources().obtainTypedArray(R.array.movie_image);
    }

    public static ArrayList<MovieEntity> generateMovieData(){
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for(int index = 0; index < dataMovieTitle.length ; index++) {
            movieEntities.add(new MovieEntity(
                    dataMovieTitle[index],
                    dataMovieDesc[index],
                    dataMovieGenre[index],
                    dataMovieRuntime[index],
                    Double.parseDouble(dataMovieScore[index]),
                    dataPoster.getResourceId(index,-1)
            ));
        }
        return movieEntities;
    }
}
