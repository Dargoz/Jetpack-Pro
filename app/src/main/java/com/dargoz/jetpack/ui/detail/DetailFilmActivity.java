package com.dargoz.jetpack.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dargoz.jetpack.ui.GenreTextView;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.viewmodel.ViewModelFactory;

import static com.dargoz.jetpack.utils.Constants.Category.URL_MOVIES;
import static com.dargoz.jetpack.utils.Constants.Category.URL_TV;
import static com.dargoz.jetpack.utils.ImageRepositoryList.findImage;

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
        setupView();
        MovieEntity movieEntity = DetailFimViewModel.prepareData(getIntent());
        viewModel = obtainViewModel(movieEntity);
        viewModel.setFilmDetails(movieEntity, DetailFimViewModel.isMovieEntity() ? URL_MOVIES : URL_TV);
        viewModel.gerFilmDetails().observe(this, getDetails);

        initData();
    }

    private DetailFimViewModel obtainViewModel(MovieEntity movieEntity){
        Log.d("DRG","Movie entity id: " + movieEntity.getId());
        Log.d("DRG","Movie entity name: " + movieEntity.getTitle());
        ViewModelFactory factory =
                ViewModelFactory.getInstance(getApplication(),
                        movieEntity,
                        DetailFimViewModel.isMovieEntity() ? URL_MOVIES : URL_TV);
        return ViewModelProviders.of(this,factory).get(DetailFimViewModel.class);
    }

    private Observer<Object> getDetails = new Observer<Object>() {
        @Override
        public void onChanged(Object filmData) {
            Log.i("DRG","getDetail Result : " + ((MovieEntity) filmData).getDuration());
            if (filmData instanceof TvShowEntity) {
                episodeText.setText(String.format("Tv Shows | %s Episode",
                        ((TvShowEntity) filmData).getTotalEpisode()));
            }
            statusText.setText(((MovieEntity) filmData).getStatus());
            runtimeText.setText(((MovieEntity) filmData).getDuration());
            showGenreList(((MovieEntity) filmData).getGenre().split(","));
            /*showLoading(false);
            movieData = (MovieEntity) filmData;*/
        }
    };

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
        if(DetailFimViewModel.isMovieEntity()){
            movieEntity = viewModel.getMovieEntity();
            episodeText.setVisibility(View.INVISIBLE);
        }else{
            movieEntity = viewModel.getTvShowEntity();
        }
        filmTitleText.setText(movieEntity.getTitle());
        filmPosterImage.setImageBitmap(findImage(movieEntity.getId()));
        filmDescText.setText(movieEntity.getDescription());
        scoreText.setText(String.valueOf(movieEntity.getScore()));
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
