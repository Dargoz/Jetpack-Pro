package com.dargoz.jetpack.ui.movie;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel implements FilmRepository.RepositoryListener {
    private MutableLiveData<List<MovieEntity>> movieItemList = new MutableLiveData<>();
    private FilmRepository filmRepository;

    public MovieViewModel(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }
    void setMovieEntities() {
        filmRepository.getAllMovies(this);
    }

    LiveData<List<MovieEntity>> getMovieList(){
        return movieItemList;
    }


    @Override
    public void onSuccess(ArrayList<MovieEntity> movieEntities) {
        Log.d("DRG","success : " + movieEntities.size());
        movieItemList.setValue(movieEntities);
    }

    @Override
    public void onError() {
        Log.d("DRG","Error");
    }
}
