package com.dargoz.jetpack.ui.tvshow;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.TvShowEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TvShowViewModelTest {
    private TvShowViewModel viewModel;
    private List<TvShowEntity> tvShowEntities = new ArrayList<>();
    @Before
    public void setUp() {
        viewModel = new TvShowViewModel();
        TvShowEntity tvShowEntity = new TvShowEntity(
                "Arrow",
                "super hero movie",
                "May 2018",
                "action, drama, super power",
                "35 minute",
                Double.parseDouble("8.5"),
                "Released",
                R.drawable.arrow    ,
                "22 Episodes"
        );
        tvShowEntities.add(tvShowEntity);
        viewModel.setTvShowEntities(tvShowEntities);
    }

    @Test
    public void getTvShowList() {
        assertEquals(tvShowEntities, viewModel.getTvShowList());
    }
}