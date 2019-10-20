package com.dargoz.jetpack.ui.tvshow;

import androidx.lifecycle.ViewModel;

import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class TvShowViewModel extends ViewModel {
    private List<TvShowEntity> tvShowEntities;

    void setTvShowEntities(List<TvShowEntity> tvShowEntities) {
        this.tvShowEntities = tvShowEntities;
    }

    List<TvShowEntity> getTvShowList(){
        return tvShowEntities;
    }
}
