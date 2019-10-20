package com.dargoz.jetpack.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dargoz.jetpack.ui.GenreTextView;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;

public class DetailFilmActivity extends AppCompatActivity {
    DetailFimViewModel viewModel;
    TextView filmTitleText;
    ImageView filmPosterImage;
    TextView episodeText;
    TextView filmDescText;
    TextView scoreText;
    TextView runtimeText;
    private LinearLayout genreGridView;
    TextView statusText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        viewModel = ViewModelProviders.of(this).get(DetailFimViewModel.class);
        viewModel.prepareData(getIntent());
        setupView();
        initData();
    }

    private void setupView(){
        filmTitleText = findViewById(R.id.detail_title_text_view);
        filmPosterImage = findViewById(R.id.movie_detail_image);
        episodeText = findViewById(R.id.episode_text_view);
        filmDescText = findViewById(R.id.desc_detail_text_view);
        scoreText = findViewById(R.id.score_text_view);
        runtimeText = findViewById(R.id.runtime_text_view);
        genreGridView = findViewById(R.id.genre_container_view);
        statusText = findViewById(R.id.status_text_view);
    }

    private void initData(){
        MovieEntity movieEntity;
        if(viewModel.isMovieEntity()){
            movieEntity = viewModel.getMovieEntity();
            episodeText.setVisibility(View.INVISIBLE);
        }else{
            TvShowEntity tvShowEntity = viewModel.getTvShowEntity();
            movieEntity = tvShowEntity;
            episodeText.setText(String.format("Tv Shows | %s",tvShowEntity.getTotalEpisode()));
        }
        filmTitleText.setText(movieEntity.getTitle());
        filmPosterImage.setImageResource(movieEntity.getImage());
        filmDescText.setText(movieEntity.getDescription());
        scoreText.setText(String.valueOf(movieEntity.getScore()));
        runtimeText.setText(movieEntity.getDuration());
        String[] genreList = movieEntity.getGenre().split(",");
        showGenreList(genreList);
        statusText.setText(movieEntity.getStatus());
    }

    private void showGenreList(String[] genreList) {
        genreGridView.removeAllViews();
        LinearLayout row = new LinearLayout(this);
        for (int idx = 0; idx < genreList.length; idx++) {
            if (idx % 3 == 0) {
                row = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(layoutParams);
            }
            GenreTextView genreText = new GenreTextView(this);
            genreText.setText(genreList[idx].trim());
            row.addView(genreText);
            if (idx % 3 == 0) {
                genreGridView.addView(row);
            }
        }
    }
}
