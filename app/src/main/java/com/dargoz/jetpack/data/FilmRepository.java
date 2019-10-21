package com.dargoz.jetpack.data;

import android.graphics.Bitmap;
import android.util.Log;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;
import com.dargoz.jetpack.utils.RemoteDBHelper;

import java.util.ArrayList;

public class FilmRepository implements DataSource, RemoteDBHelper.ResponseListener,
        RemoteDBHelper.ImageResponseListener, RemoteDBHelper.TvResponseListener {
    private volatile static FilmRepository INSTANCE = null;
    private RemoteRepository remoteRepository;
    private RepositoryListener repositoryListener;
    private TvRepositoryListener tvRepositoryListener;
    private ImageRepositoryListener imageRepositoryListener;

    private FilmRepository(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public void setRemoteRepository(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public static FilmRepository getInstance(RemoteRepository remoteRepository) {
        if(INSTANCE  == null){
            INSTANCE = new FilmRepository(remoteRepository);
        }
        return INSTANCE;
    }

    public interface RepositoryListener {
        void onSuccess(ArrayList<MovieEntity> movieEntities);
        void onError();
    }

    public interface TvRepositoryListener {
        void onSuccess(ArrayList<TvShowEntity> tvShowEntities);
        void onError();
    }

    public interface ImageRepositoryListener {
        void onImageResponse(MovieEntity movieEntity, Bitmap bitmap);
        void onImageError(MovieEntity movieEntity);
    }

    @Override
    public void getAllMovies(RepositoryListener movieResponseListener) {
        Log.w("DRG","get All Movie");
        remoteRepository.getAllMovies(this);
        this.repositoryListener = movieResponseListener;
    }

    @Override
    public void getAllTvShows(TvRepositoryListener tvRepositoryListener) {
        Log.w("DRG","get All Tv");
        remoteRepository.getAllTvShows(this);
        this.tvRepositoryListener = tvRepositoryListener;
    }


    @Override
    public void onResponse(ArrayList<MovieResponse> movieResponses) {
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for(MovieResponse movieResponse : movieResponses){
            MovieEntity movieEntity = new MovieEntity(
                    movieResponse.getId(),
                    movieResponse.getTitle(),
                    movieResponse.getDescription(),
                    movieResponse.getReleaseDate(),
                    movieResponse.getGenre(),
                    movieResponse.getDuration(),
                    Double.parseDouble(movieResponse.getScore()),
                    movieResponse.getStatus(),
                    movieResponse.getImagePath()
            );
            movieEntities.add(movieEntity);
        }
        repositoryListener.onSuccess(movieEntities);
    }

    @Override
    public void onTvResponse(ArrayList<TvShowResponse> tvShowResponses) {
        ArrayList<TvShowEntity> tvShowEntities = new ArrayList<>();
        for(TvShowResponse tvShowResponse : tvShowResponses){
            TvShowEntity tvShowEntity = new TvShowEntity(
                    tvShowResponse.getId(),
                    tvShowResponse.getTitle(),
                    tvShowResponse.getDescription(),
                    tvShowResponse.getReleaseDate(),
                    tvShowResponse.getGenre(),
                    tvShowResponse.getDuration(),
                    Double.parseDouble(tvShowResponse.getScore()),
                    tvShowResponse.getStatus(),
                    tvShowResponse.getImagePath()
            );
            tvShowEntities.add(tvShowEntity);
        }
        tvRepositoryListener.onSuccess(tvShowEntities);
    }

    @Override
    public void onError() {

    }

    @Override
    public void getMovieImage(MovieEntity movieEntity, ImageRepositoryListener imageRepositoryListener) {
        remoteRepository.getImage(movieEntity, this);
        this.imageRepositoryListener = imageRepositoryListener;
    }

    @Override
    public void onImageResponse(MovieEntity movieEntity, Bitmap bitmap) {
        imageRepositoryListener.onImageResponse(movieEntity, bitmap);
    }

    @Override
    public void onImageError(MovieEntity movieEntity) {
        imageRepositoryListener.onImageError(movieEntity);
    }

}
