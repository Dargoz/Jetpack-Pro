package com.dargoz.jetpack.ui.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.MovieEntity;
import com.dargoz.jetpack.utils.Utils;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<MovieEntity> movieEntities;

    public void setMovieEntities(List<MovieEntity> movieEntities){
        this.movieEntities = movieEntities;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item_list, parent, false);
        context = itemRow.getContext();
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindViewData(movieEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView moviePoster;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieScore;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_image_view);
            movieTitleText = itemView.findViewById(R.id.movie_title_text_view);
            movieReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            movieScore = itemView.findViewById(R.id.rating_text_view);

        }

        void bindViewData(final MovieEntity movieEntity){
            Glide.with(context)
                    .load(movieEntity.getImage())
                    .placeholder(R.drawable.baseline_broken_image_white_24)
                    .apply(new RequestOptions()
                            .override(context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.main_poster_width),
                                    context
                                            .getResources()
                                            .getDimensionPixelSize(R.dimen.main_poster_height))
                            .transform(new RoundedCorners(context
                                    .getResources()
                                    .getDimensionPixelSize(R.dimen.poster_corner))))
                    .into(moviePoster);
            movieTitleText.setText(movieEntity.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(movieEntity.getReleaseDate()));
            movieScore.setText(String.valueOf(movieEntity.getScore()));
        }
    }
}
