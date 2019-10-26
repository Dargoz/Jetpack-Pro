package com.dargoz.jetpack.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dargoz.jetpack.R;

public class Constants {
    private static final String API_KEY = "043a5f6ab7599c142a42ec784d0aaf08";

    public static final String MOVIE_INTENT = "movieEntity";
    public static final String TV_SHOW_INTENT = "tvShowEntity";

    public static final String KEY_TOTAL_EPISODE = "number_of_episodes";
    public static final String MOVIES_KEY_RUNTIME = "runtime";
    public static final String TV_KEY_RUNTIME = "episode_run_time";

    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";

    public enum Type {
        URL_TYPE_SEARCH,
        URL_TYPE_DISCOVER,
        URL_TYPE_NEW_RELEASE,
        URL_TYPE_DETAIL
    }

    public enum Category{
        URL_MOVIES("movie"),
        URL_TV("tv");
        private String value;

        Category(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
    }

    @NonNull
    public static String getUrlOf(@NonNull Type type, Category category , String keyword, Context context){
        switch (type){
            case URL_TYPE_DISCOVER :
                return "https://api.themoviedb.org/3/discover/"+ category.getValue()
                        + "?language=" + context.getResources().getString(R.string.default_language)
                        + "&api_key=" + API_KEY;
            case URL_TYPE_SEARCH :
                return "https://api.themoviedb.org/3/search/"+ category.getValue()
                        + "?api_key=" + API_KEY
                        + "&language=" + context.getResources().getString(R.string.default_language)
                        + "&query=" + keyword;
            case URL_TYPE_NEW_RELEASE:
                return "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + API_KEY
                        + "&primary_release_date.gte=" + keyword
                        + "&primary_release_date.lte=" + keyword;
            default:
                return "https://api.themoviedb.org/3/"+ category.getValue() + "/" + keyword +
                        "?language=" + context.getResources().getString(R.string.default_language) +
                        "&api_key=" + API_KEY;
        }
    }
}
