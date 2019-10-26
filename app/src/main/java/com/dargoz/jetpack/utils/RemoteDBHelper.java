package com.dargoz.jetpack.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RemoteDBHelper {
    private String url;

    public RemoteDBHelper(String url) {
        this.url = url;
    }

    public interface ResponseListener {
        void onResponse(ArrayList<MovieResponse> movieResponses);
        void onError();
    }

    public interface TvResponseListener {
        void onTvResponse(ArrayList<TvShowResponse> tvShowResponses);
        void onError();
    }

    public void loadMovies(final ResponseListener responseListener) {
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
                            responseListener.onResponse(movieResponses);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        responseListener.onError();
                    }
                });
    }

    public void loadAllTvShows(final TvResponseListener responseListener){
        Log.i("DRG","url : " + url);
        final ArrayList<TvShowResponse> tvShowResponses = new ArrayList<>();
        AndroidNetworking.get(url)
                .setTag("tvShow")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            int totalItem = results.length();
                            for (int i = 0; i < totalItem; i++) {
                                JSONObject tvShowObject = results.getJSONObject(i);
                                tvShowResponses.add(new TvShowResponse(tvShowObject));
                            }
                            responseListener.onTvResponse(tvShowResponses);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w("DRG","Error Tv " + anError.getErrorDetail());
                        responseListener.onError();
                    }
                });
    }

    public interface ImageResponseListener {
        void onImageResponse(MovieEntity movieEntity, Bitmap bitmap);
        void onImageError(MovieEntity movieEntity);
    }

    public void loadImage(final MovieEntity movieEntity, final ImageResponseListener listener) {
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
                        listener.onImageError(movieEntity);
                    }
                });
    }

    public interface DetailsListener {
      void onDetailResponse(MovieEntity movieEntity, Constants.Category category,
                            JSONObject response);
    }

    public void loadDetails(final MovieEntity movieEntity, final Constants.Category category,
                            final DetailsListener listener){
        Log.i("DRG","url detail : " + url);
        AndroidNetworking.get(url)
                .setTag("filmDetail")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onDetailResponse(movieEntity, category, response);
                    }

                    @Override
                    public void onError(ANError anError) { }
                });

    }

}
