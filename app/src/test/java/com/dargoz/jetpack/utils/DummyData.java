package com.dargoz.jetpack.utils;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;

import java.util.ArrayList;
import java.util.List;

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

    public static List<MovieEntity> generateDummyMovieEntities() {
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        MovieEntity movieEntity = new MovieEntity("Infinity War",
                "final battle avenger with Thanos",
                "2-10-2018",
                "super hero",
                "190",
                8.6,
                "Released",
                8);
        movieEntities.add(movieEntity);
        return movieEntities;
    }
    public static List<TvShowEntity> generateDummyTvShowEntities() {
        ArrayList<TvShowEntity> tvShowEntities = new ArrayList<>();
        TvShowEntity tvShowEntity = new TvShowEntity("Arrow",
                "from DC comic super heroes series",
                "2-10-2017",
                "super hero",
                "190",
                8.6,
                "Released",
                8,"20 episodes");
        tvShowEntities.add(tvShowEntity);
        return tvShowEntities;
    }
}
