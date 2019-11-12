package com.dargoz.jetpack.ui.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.MovieEntity;
import com.dargoz.jetpack.ui.detail.DetailFilmActivity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static com.dargoz.jetpack.utils.ImageRepositoryList.findImage;

public class MoviePagedListAdapter extends PagedListAdapter<MovieEntity, MoviePagedListAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieEntity> movieEntities;

    private static DiffUtil.ItemCallback<MovieEntity> diffCallback = new DiffUtil.ItemCallback<MovieEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected MoviePagedListAdapter() {
        super(diffCallback);
    }


    void setMovieEntities(List<MovieEntity> movieEntities){
        this.movieEntities = movieEntities;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item_list, parent, false);
        context = itemRow.getContext();
        return new MovieViewHolder(itemRow);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindViewData(movieEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private final ShimmerFrameLayout shimmerFrameLayout;
        private final FrameLayout movieContainer;
        private final ImageView moviePoster;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieScore;


        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.movie_item_shimmer_layout);
            movieContainer = itemView.findViewById(R.id.movie_image_container);
            moviePoster = itemView.findViewById(R.id.movie_image_view);
            movieTitleText = itemView.findViewById(R.id.movie_title_text_view);
            movieReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            movieScore = itemView.findViewById(R.id.rating_text_view);

        }

        void bindViewData(final MovieEntity movieEntity){
            Bitmap bitmap = findImage(movieEntity.getId());
            shimmerFrameLayout.startShimmer();
            if(bitmap != null){
                if(shimmerFrameLayout.isShimmerStarted()){
                    shimmerFrameLayout.stopShimmer();
                }
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            movieContainer.setOnClickListener(view -> navigateView(movieEntity));
            Glide.with(context)
                    .load(bitmap)
                    .placeholder(R.drawable.rounded_rect_grey)
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

        private void navigateView(MovieEntity movieEntity){
            Intent intent = new Intent(context, DetailFilmActivity.class);
            intent.putExtra(Constants.MOVIE_INTENT, movieEntity);
            context.startActivity(intent);
        }
    }

}
