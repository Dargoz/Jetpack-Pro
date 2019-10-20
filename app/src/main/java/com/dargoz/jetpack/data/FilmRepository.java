package com.dargoz.jetpack.data;

import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.remote.RemoteRepository;
import com.dargoz.jetpack.data.source.remote.response.MovieResponse;
import com.dargoz.jetpack.utils.RemoteDBHelper;

import java.util.ArrayList;

public class FilmRepository implements DataSource, RemoteDBHelper.MovieResponseListener {
    private volatile static FilmRepository INSTANCE = null;
    private RemoteRepository remoteRepository;
    private RepositoryListener repositoryListener;

    private FilmRepository(RemoteRepository remoteRepository) {
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

    @Override
    public void getAllMovies(RepositoryListener movieResponseListener) {
        remoteRepository.getAllMovies(this);
        this.repositoryListener = movieResponseListener;
    }

    @Override
    public void onResponse(ArrayList<MovieResponse> movieResponses) {
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        for(MovieResponse movieResponse : movieResponses){
            MovieEntity movieEntity = new MovieEntity(
                     movieResponse.getTitle(),
                    movieResponse.getDescription(),
                    movieResponse.getReleaseDate(),
                    movieResponse.getGenre(),
                    movieResponse.getDuration(),
                    Double.parseDouble(movieResponse.getScore()),
                    movieResponse.getStatus(),
                    0
            );
            movieEntities.add(movieEntity);
        }
        repositoryListener.onSuccess(movieEntities);
    }

    @Override
    public void onError() {

    }
}
