package com.dargoz.jetpack.ui.movie;


import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieViewModelTest {
    private MovieViewModel viewModel;
    private List<MovieEntity> movieEntities = new ArrayList<>();
    @Before
    public void setUp() {
        viewModel = new MovieViewModel();
        MovieEntity movieEntity = new MovieEntity(
                "Avengers: Infinity War",
                "super hero movie",
                "May 2018",
                "action, drama, super power",
                "135 minute",
                Double.parseDouble("8.5"),
                "Released",
                R.drawable.poster_avengerinfinity
                );
        movieEntities.add(movieEntity);
        viewModel.setMovieEntities(movieEntities);
    }

    @Test
    public void getMovieList() {
        assertEquals(movieEntities,viewModel.getMovieList());
    }
}