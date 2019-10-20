package com.dargoz.jetpack.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dargoz.jetpack.R;

public class Constants {
    private static final String API_KEY = "043a5f6ab7599c142a42ec784d0aaf08";

    public static final String MOVIE_INTENT = "movieEntity";
    public static final String TV_SHOW_INTENT = "tvShowEntity";

    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";

    public static final String URL_TYPE_SEARCH = "search";
    public static final String URL_TYPE_DISCOVER = "discover";
    public static final String URL_TYPE_DETAIL = "detail";
    public static final String URL_TYPE_NEW_RELEASE = "release";
    public static final String URL_MOVIES = "movie";
    public static final String URL_TV = "tv";

    @NonNull
    public static String getUrlOf(@NonNull String type, String category , String keyword, Context context){
        switch (type){
            case URL_TYPE_DISCOVER :
                return "https://api.themoviedb.org/3/discover/"+ category
                        + "?language=" + context.getResources().getString(R.string.default_language)
                        + "&api_key=" + API_KEY;
            case URL_TYPE_SEARCH :
                return "https://api.themoviedb.org/3/search/"+ category
                        + "?api_key=" + API_KEY
                        + "&language=" + context.getResources().getString(R.string.default_language)
                        + "&query=" + keyword;
            case URL_TYPE_NEW_RELEASE:
                return "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + API_KEY
                        + "&primary_release_date.gte=" + keyword
                        + "&primary_release_date.lte=" + keyword;
            default:
                return "https://api.themoviedb.org/3/"+ category + "/" + keyword +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + API_KEY;
        }
    }
}
