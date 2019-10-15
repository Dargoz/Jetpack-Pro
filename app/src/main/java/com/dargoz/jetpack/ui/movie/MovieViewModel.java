package com.dargoz.jetpack.ui.movie;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dargoz.jetpack.data.MovieEntity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.DummyData;
import com.dargoz.jetpack.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<MovieEntity>> movieItemList;

    void setMovieEntities(String url) {
        AndroidNetworking.get(url)
                .setTag("movies")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =  response.getJSONArray("results");
                            totalItem = results.length();
                            for(int i=0; i< totalItem;i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                final MovieEntity moviesItem = new MovieEntity(movieObject);
                                AndroidNetworking.get(
                                        Utils.getObjectImageUrl(
                                                Constants.IMAGE_URL,
                                                Constants.IMAGE_SIZE_W500,
                                                moviesItem.getImagePath()
                                        ))
                                        .setTag("imageRequestTag")
                                        .setPriority(Priority.HIGH)
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                loadedItemCounter++;
                                                ArrayList<MovieEntity> movieEntities = new ArrayList<>();
                                                FilmImageRepository.imageList.add(response);
                                                moviesItem.setImageId(response.getGenerationId());
                                                movieEntities.add(moviesItem);
                                                movieItemList.setValue(movieEntities);

                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                loadedItemCounter++;

                                            }
                                        });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mView.showReloadButton(true);
                    }
                });
    }

    LiveData<List<MovieEntity>> getMovieList(){
        return movieItemList;
    }
}
