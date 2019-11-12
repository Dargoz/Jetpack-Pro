package com.dargoz.jetpack.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.dargoz.jetpack.data.source.local.LocalRepository;
import com.dargoz.jetpack.data.source.local.entity.GenreEntity;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.data.source.remote.response.TvShowResponse;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.RemoteDBHelper;
import com.dargoz.jetpack.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmRepository implements DataSource, RemoteDBHelper.ResponseListener,
        RemoteDBHelper.ImageResponseListener, RemoteDBHelper.TvResponseListener,
        RemoteDBHelper.DetailsListener{
    private volatile static FilmRepository INSTANCE = null;

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;
    private RepositoryListener repositoryListener;
    private TvRepositoryListener tvRepositoryListener;
    private ImageRepositoryListener imageRepositoryListener;
    private DetailsRepositoryListener detailsRepositoryListener;

    private FilmRepository(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public void setRemoteRepository(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public static FilmRepository getInstance(LocalRepository localRepository, RemoteRepository remoteRepository) {
        if(INSTANCE  == null){
            INSTANCE = new FilmRepository(localRepository, remoteRepository);
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

    public interface DetailsRepositoryListener {
        void onDetailsDataResponse (Object object);
    }

    @Override
    public void getAllMovies(RepositoryListener movieResponseListener) {
        remoteRepository.getAllMovies(this);
        this.repositoryListener = movieResponseListener;
    }

    @Override
    public void getAllTvShows(TvRepositoryListener tvRepositoryListener) {
        remoteRepository.getAllTvShows(this);
        this.tvRepositoryListener = tvRepositoryListener;
    }


    @Override
    public void onResponse(ArrayList<MovieResponse> movieResponses) {
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        try {
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
        } catch (Exception e) {
            repositoryListener.onError();
        }
    }

    @Override
    public void onError() {
        repositoryListener.onError();
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
    public void onTvError() {
        tvRepositoryListener.onError();
    }

    @Override
    public void getFilmImage(MovieEntity movieEntity, ImageRepositoryListener imageRepositoryListener) {
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

    @Override
    public void getFilmDetails(MovieEntity movieEntity, Constants.Category category,
                               DetailsRepositoryListener listener) {
        remoteRepository.getDetails(movieEntity, category, this);
        this.detailsRepositoryListener = listener;
    }

    @Override
    public void onDetailResponse(MovieEntity movieEntity, Constants.Category category,
                                 JSONObject response) {
        try {
            JSONArray genresListResponse = response.getJSONArray("genres");
            ArrayList<GenreEntity> genresList = new ArrayList<>();
            for(int idx = 0 ; idx < genresListResponse.length(); idx++){
                JSONObject genreObject = genresListResponse.getJSONObject(idx);
                GenreEntity genreEntity = new GenreEntity(genreObject);
                genresList.add(genreEntity);
            }
            movieEntity.setGenre(genresList);
            movieEntity.setStatus(response.getString("status"));
            if (Constants.Category.URL_TV == category) {
                JSONArray runtimeResponse =
                        response.getJSONArray(Constants.TV_KEY_RUNTIME);
                movieEntity.setDuration(Utils.formatRuntime(
                        runtimeResponse.getInt(0)));
                TvShowEntity tvShow = (TvShowEntity) movieEntity;
                tvShow.setTotalEpisode(String.valueOf(
                        response.getInt(Constants.KEY_TOTAL_EPISODE)
                ));
                detailsRepositoryListener.onDetailsDataResponse(tvShow);
            } else {
                movieEntity.setDuration(Utils.formatRuntime(
                        response.getInt(Constants.MOVIES_KEY_RUNTIME)));
                detailsRepositoryListener.onDetailsDataResponse(movieEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LiveData<PagedList<MovieEntity>> getFavoriteMovies() {
        return new LivePagedListBuilder<>(localRepository.getAllMovies(),20).build() ;
    }
}
