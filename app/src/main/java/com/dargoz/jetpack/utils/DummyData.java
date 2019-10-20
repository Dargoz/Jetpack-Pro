package com.dargoz.jetpack.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

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

    private static String[] dataTvShowTitle;
    private static String[] dataTvShowReleaseDate;
    private static String[] dataTvShowTotalEpisode;
    private static String[] dataTvShowDesc;
    private static String[] dataTvShowGenre;
    private static String[] dataTvShowStatus;
    private static String[] dataTvShowScore;
    private static String[] dataTvShowRuntime;
    private static TypedArray dataTvShowPoster;


    public static void prepareMovieData(Context context){
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

    public static void prepareTvShowData(Context context){
        dataTvShowTitle = context
                .getResources().getStringArray(R.array.tv_title);
        dataTvShowReleaseDate = context
                .getResources().getStringArray(R.array.tv_release_date);
        dataTvShowTotalEpisode = context
                .getResources().getStringArray(R.array.tv_total_episode);
        dataTvShowDesc = context
                .getResources().getStringArray(R.array.tv_desc);
        dataTvShowGenre = context
                .getResources().getStringArray(R.array.tv_genre);
        dataTvShowStatus =context
                .getResources().getStringArray(R.array.tv_release_status);
        dataTvShowScore = context
                .getResources().getStringArray(R.array.tv_score);
        dataTvShowRuntime = context
                .getResources().getStringArray(R.array.tv_runtime);
        dataTvShowPoster = context
                .getResources().obtainTypedArray(R.array.tv_image);
    }

    public static ArrayList<MovieEntity> generateMovieData(){
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for(int index = 0; index < dataMovieTitle.length ; index++) {
            movieEntities.add(new MovieEntity(
                    dataMovieTitle[index],
                    dataMovieDesc[index],
                    dataMovieReleaseDate[index],
                    dataMovieGenre[index],
                    dataMovieRuntime[index],
                    Double.parseDouble(dataMovieScore[index]),
                    dataMovieStatus[index],
                    dataPoster.getResourceId(index,-1)
            ));
        }
        return movieEntities;
    }

    public static ArrayList<TvShowEntity> generateTvShowData(){
        ArrayList<TvShowEntity> tvShowEntities = new ArrayList<>();
        for(int index = 0; index < dataTvShowTitle.length ; index++) {
            tvShowEntities.add(new TvShowEntity(
                    dataTvShowTitle[index],
                    dataTvShowDesc[index],
                    dataTvShowReleaseDate[index],
                    dataTvShowGenre[index],
                    dataTvShowRuntime[index],
                    Double.parseDouble(dataTvShowScore[index]),
                    dataTvShowStatus[index],
                    dataTvShowPoster.getResourceId(index,-1),
                    dataTvShowTotalEpisode[index]
            ));
        }
        return tvShowEntities;
    }
}
