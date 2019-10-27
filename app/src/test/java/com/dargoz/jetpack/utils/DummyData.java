package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;

import java.util.ArrayList;

public class DummyData {

    public static ArrayList<MovieResponse> generateMovieResponse(){
        ArrayList<MovieResponse> movieResponses = new ArrayList<>();
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setMovieResponse(
                "24",
                "Infinity War",
                "super hero",
                "2-10-2018",
                "8.6",
                "/imagePath"
        );
        movieResponses.add(movieResponse);
        return movieResponses;
    }

    public static ArrayList<TvShowResponse> generateTvShowResponse(){
        ArrayList<TvShowResponse> tvShowResponses = new ArrayList<>();
        TvShowResponse tvShowResponse = new TvShowResponse();
        tvShowResponse.setTvResponse(
                "01",
                "Arrow",
                "super hero",
                "2-10-2017",
                "8.6",
                "/imagePath",
                "20 Episode"
        );
        tvShowResponses.add(tvShowResponse);
        return tvShowResponses;
    }

    public static MovieEntity generateMovieEntity(){
        return new MovieEntity(
                "31",
                "Deadpool",
                "comedy super hero movie",
                "2017",
                "Action, Comedy",
                "125",8.6,
                "released",
                "");
    }


}
