package com.dargoz.jetpack.ui.tvshow;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.FilmRepository;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.data.source.local.entity.ImageEntity;
import com.dargoz.jetpack.utils.ImageRepositoryList;
import com.dargoz.jetpack.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TvShowViewModel extends ViewModel implements FilmRepository.TvRepositoryListener,
        FilmRepository.ImageRepositoryListener{
    private MutableLiveData<List<TvShowEntity>> tvShowItemList = new MutableLiveData<>();
    private ArrayList<TvShowEntity> tvShowEntities = new ArrayList<>();
    private FilmRepository filmRepository;

    public TvShowViewModel(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    void setTvShowEntities() {
        filmRepository.getAllTvShows(this);
    }

    LiveData<List<TvShowEntity>> getTvShowList(){
        return tvShowItemList;
    }

    @Override
    public void onSuccess(ArrayList<TvShowEntity> tvShowEntities) {
        tvShowItemList.setValue(tvShowEntities);
        this.tvShowEntities = tvShowEntities;
        for(TvShowEntity tvShowEntity : tvShowEntities){
            filmRepository.getMovieImage(tvShowEntity, this);
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onImageResponse(MovieEntity movieEntity, Bitmap bitmap) {
        ImageRepositoryList.addImage(new ImageEntity(movieEntity.getId(), bitmap));
        tvShowItemList.setValue(tvShowEntities);
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
