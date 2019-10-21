package com.dargoz.jetpack.data.source.remote;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.utils.RemoteDBHelper;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private RemoteDBHelper remoteDBHelper;

    private RemoteRepository(RemoteDBHelper remoteDBHelper) {
        this.remoteDBHelper = remoteDBHelper;
    }

    public static RemoteRepository getInstance(RemoteDBHelper remoteDBHelper) {
        if(INSTANCE == null) {
            INSTANCE = new RemoteRepository(remoteDBHelper);
        }else {
            INSTANCE.remoteDBHelper = remoteDBHelper;
        }
        return INSTANCE;
    }

    public void getAllMovies(RemoteDBHelper.ResponseListener listener){
        remoteDBHelper.loadMovies(listener);
    }

    public void getAllTvShows(RemoteDBHelper.TvResponseListener listener){
        remoteDBHelper.loadAllTvShows(listener);
    }

    public void getImage(MovieEntity movieEntity, RemoteDBHelper.ImageResponseListener listener){
        remoteDBHelper.loadImage(movieEntity, listener);
    }
}
