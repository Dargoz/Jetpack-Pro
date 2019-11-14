package com.dargoz.jetpack.ui.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.ui.GenreTextView;
import com.dargoz.jetpack.viewmodel.ViewModelFactory;

import static com.dargoz.jetpack.utils.Constants.Category.URL_MOVIES;
import static com.dargoz.jetpack.utils.Constants.Category.URL_TV;
import static com.dargoz.jetpack.utils.ImageRepositoryList.findImage;

public class DetailFilmActivity extends AppCompatActivity implements View.OnClickListener {
    private DetailFimViewModel viewModel;
    private TextView filmTitleText;
    private ImageView filmPosterImage;
    private TextView episodeText;
    private TextView filmDescText;
    private TextView scoreText;
    private TextView runtimeText;
    private LinearLayout genreGridView;
    private TextView statusText;
    private ImageView bookmarkIcon;
    private HandlerThread handlerThread;
    private Handler handler;
    boolean isBookmarked = false;

    MovieEntity movieEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        setupView();
        movieEntity = DetailFimViewModel.prepareData(getIntent());
        viewModel = obtainViewModel(movieEntity);
        viewModel.setFilmDetails(movieEntity, DetailFimViewModel.isMovieEntity() ? URL_MOVIES : URL_TV);
        viewModel.gerFilmDetails().observe(this, getDetails);

        initData();
    }

    private DetailFimViewModel obtainViewModel(MovieEntity movieEntity) {
        ViewModelFactory factory =
                ViewModelFactory.getInstance(getApplication(),
                        movieEntity,
                        DetailFimViewModel.isMovieEntity() ? URL_MOVIES : URL_TV);
        return ViewModelProviders.of(this, factory).get(DetailFimViewModel.class);
    }

    private final Observer<Object> getDetails = filmData -> {
        if (filmData instanceof TvShowEntity) {
            episodeText.setText(String.format("Tv Shows | %s Episode",
                    ((TvShowEntity) filmData).getTotalEpisode()));
        }
        statusText.setText(((MovieEntity) filmData).getStatus());
        runtimeText.setText(((MovieEntity) filmData).getDuration());
        showGenreList(((MovieEntity) filmData).getGenre().split(","));
    };

    private void setupView() {
        filmTitleText = findViewById(R.id.detail_title_text_view);
        bookmarkIcon = findViewById(R.id.add_to_favorite_button);
        filmPosterImage = findViewById(R.id.movie_detail_image);
        episodeText = findViewById(R.id.episode_text_view);
        filmDescText = findViewById(R.id.desc_detail_text_view);
        scoreText = findViewById(R.id.score_text_view);
        runtimeText = findViewById(R.id.runtime_text_view);
        genreGridView = findViewById(R.id.genre_container_view);
        statusText = findViewById(R.id.status_text_view);
        bookmarkIcon.setOnClickListener(this);
    }

    private void initData() {

        if (DetailFimViewModel.isMovieEntity()) {
            movieEntity = viewModel.getMovieEntity();
            episodeText.setVisibility(View.INVISIBLE);
        } else {
            movieEntity = viewModel.getTvShowEntity();
        }

        initializeBookmarkIcon();

        filmTitleText.setText(movieEntity.getTitle());
        Bitmap imagePoster = findImage(movieEntity.getId());
        filmPosterImage.setImageBitmap(imagePoster == null ?
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.baseline_broken_image_white_24) : imagePoster);
        filmDescText.setText(movieEntity.getDescription());
        scoreText.setText(String.valueOf(movieEntity.getScore()));
    }

    private void initializeBookmarkIcon() {
        handlerThread = new HandlerThread(this.toString());
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        handler.post(() -> {
            if (viewModel.isMovieInFavoriteList(movieEntity)) {
                bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_white_36);
                isBookmarked = true;
            } else {
                bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_white_36);
                isBookmarked = false;
            }
        });
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

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.add_to_favorite_button) {
            if (!isBookmarked) {
                viewModel.addToFavoriteMovieList(movieEntity);
                bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_white_36);
            } else {
                viewModel.deleteMovieFromFavoriteList(movieEntity);
                bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_white_36);
            }

        }
    }

    @Override
    protected void onDestroy() {
        handlerThread.quitSafely();
        super.onDestroy();
    }
}
