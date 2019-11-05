package com.dargoz.jetpack.ui.movie;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.ImageEntity;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.utils.ImageRepositoryList;
import com.dargoz.jetpack.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel
        implements FilmRepository.RepositoryListener, FilmRepository.ImageRepositoryListener {
    private final MutableLiveData<List<MovieEntity>> movieItemList = new MutableLiveData<>();
    private ArrayList<MovieEntity> movieEntities = new ArrayList<>();
    private final FilmRepository filmRepository;
    private  ErrorListener errorListener;
    interface ErrorListener {
        void onResponseError();
    }

    public MovieViewModel(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    void setMovieEntities() {
        filmRepository.getAllMovies(this);
    }

    void setErrorCallbackListener(ErrorListener errorCallbackListener){
        this.errorListener = errorCallbackListener;
    }

    LiveData<List<MovieEntity>> getMovieList(){
        return movieItemList;
    }


    @Override
    public void onSuccess(ArrayList<MovieEntity> movieEntities) {
        movieItemList.setValue(movieEntities);
        this.movieEntities = movieEntities;
        for(MovieEntity movieEntity : movieEntities) {
            filmRepository.getFilmImage(movieEntity, this);
        }
    }

    @Override
    public void onError() {
        errorListener.onResponseError();
    }

    @Override
    public void onImageResponse(MovieEntity movieEntity, Bitmap bitmap) {
        ImageRepositoryList.addImage(new ImageEntity(movieEntity.getId(), bitmap));
        movieItemList.setValue(movieEntities);
    }

    @Override
    public void onImageError(MovieEntity movieEntity) {
        ImageRepositoryList.addImage(
                new ImageEntity(
                        movieEntity.getId(),
                        Utils.getBitmapFromDrawable(R.drawable.baseline_broken_image_white_24)
                )
        );
    }
}
