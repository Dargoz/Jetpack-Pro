package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RemoteDBHelper {
    private String url;

    public RemoteDBHelper(String url) {
        this.url = url;
    }

    public interface MovieResponseListener {
        void onResponse(ArrayList<MovieResponse> movieResponses);
        void onError();
    }

    public void loadMovies(final MovieResponseListener movieResponseListener) {
        final ArrayList<MovieResponse> movieResponses = new ArrayList<>();
        AndroidNetworking.get(url)
                .setTag("movies")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            int totalItem = results.length();
                            for (int i = 0; i < totalItem; i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                movieResponses.add(new MovieResponse(movieObject));
                            }
                            movieResponseListener.onResponse(movieResponses);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        movieResponseListener.onError();
                    }
                });
    }

    public interface MovieImageResponseListener {
        void onImageResponse(MovieEntity movieEntity, Bitmap bitmap);
        void onImageError();
    }

    public void loadImage(final MovieEntity movieEntity, final MovieImageResponseListener listener) {
        AndroidNetworking.get(
                Utils.getObjectImageUrl(
                        Constants.IMAGE_URL,
                        Constants.IMAGE_SIZE_W500,
                        movieEntity.getImagePath()
                ))
                .setTag("imageRequestTag")
                .setPriority(Priority.HIGH)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        listener.onImageResponse(movieEntity, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onImageError();
                    }
                });
    }
}
