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
        }
        return INSTANCE;
    }

    public void getAllMovies(RemoteDBHelper.MovieResponseListener listener){
        remoteDBHelper.loadMovies(listener);
    }

    public void getImage(MovieEntity movieEntity, RemoteDBHelper.MovieImageResponseListener listener){
        remoteDBHelper.loadImage(movieEntity, listener);
    }
}
