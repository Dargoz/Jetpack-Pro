package com.dargoz.jetpack.ui.utils;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;

public class FakeData {

    public static MovieEntity getMovieEntity(){
        return new MovieEntity(
                "Avengers: Infinity War",
                "super hero movie",
                "May 2018",
                "action, drama, super power",
                "135 minute",
                Double.parseDouble("8.5"),
                "Released",
                R.drawable.poster_avengerinfinity
        );
    }

}
