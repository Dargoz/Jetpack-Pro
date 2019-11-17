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

public class FakeFilmRepository implements DataSource, RemoteDBHelper.ResponseListener,
        RemoteDBHelper.ImageResponseListener, RemoteDBHelper.TvResponseListener,
        RemoteDBHelper.DetailsListener{

    private LocalRepository localRepository;
    private final RemoteRepository remoteRepository;
    private FilmRepository.RepositoryListener repositoryListener;
    private FilmRepository.TvRepositoryListener tvRepositoryListener;
    private FilmRepository.ImageRepositoryListener imageRepositoryListener;
    private FilmRepository.DetailsRepositoryListener detailsRepositoryListener;

    private RemoteDBHelper.ImageResponseListener imageResponseListener;
    private RemoteDBHelper.DetailsListener detailsListener;

    FakeFilmRepository(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    void setImageResponseListener(RemoteDBHelper.ImageResponseListener imageResponseListener) {
        this.imageResponseListener = imageResponseListener;
    }

    void setDetailsListener(RemoteDBHelper.DetailsListener detailsListener) {
        this.detailsListener = detailsListener;
    }


    @Override
    public void getAllMovies(FilmRepository.RepositoryListener movieResponseListener) {
        remoteRepository.getAllMovies(this);
        this.repositoryListener = movieResponseListener;
    }

    @Override
    public void getAllTvShows(FilmRepository.TvRepositoryListener tvRepositoryListener) {
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
    public void onError() {

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

    }

    @Override
    public void getFilmImage(MovieEntity movieEntity, FilmRepository.ImageRepositoryListener imageRepositoryListener) {
        remoteRepository.getImage(movieEntity, imageResponseListener);
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
                               FilmRepository.DetailsRepositoryListener listener) {
        remoteRepository.getDetails(movieEntity, category, detailsListener);
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

    public void insertMovie(MovieEntity movieEntity) {
        localRepository.insertMovie(movieEntity);
    }

    public MovieEntity findMovieById(int id) {
        return localRepository.findMovieById(id);
    }

    public void deleteMovieFromFavorite(MovieEntity movieEntity) {
        localRepository.deleteMovie(movieEntity);
    }

    public LiveData<PagedList<TvShowEntity>> getFavoriteTvShows() {
        return new LivePagedListBuilder<>(localRepository.getAllTvShows(),20).build() ;
    }

    public void insertTvShow(TvShowEntity tvShowEntitiy) {
        localRepository.insertTvShow(tvShowEntitiy);
    }

    public TvShowEntity findTvShowById(int id) {
        return localRepository.findTvShowById(id);
    }

    public void deleteTvShowFromFavorite(TvShowEntity tvShowEntitiy) {
        localRepository.deleteTvShow(tvShowEntitiy);
    }
}
