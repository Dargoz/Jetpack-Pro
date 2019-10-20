package com.dargoz.jetpack.ui.tvshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dargoz.jetpack.R;
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.ui.detail.DetailFilmActivity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.Utils;

import java.util.List;

public class TvShowRecyclerViewAdapter extends RecyclerView.Adapter<TvShowRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<TvShowEntity> tvShowEntities;

    void setTvShowEntities(List<TvShowEntity> tvShowEntities){
        this.tvShowEntities = tvShowEntities;
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
        holder.bindViewData(tvShowEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final FrameLayout movieContainer;
        private final ImageView moviePoster;
        private final TextView movieTitleText;
        private final TextView movieReleaseDateText;
        private final TextView movieScore;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieContainer = itemView.findViewById(R.id.movie_image_container);
            moviePoster = itemView.findViewById(R.id.movie_image_view);
            movieTitleText = itemView.findViewById(R.id.movie_title_text_view);
            movieReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            movieScore = itemView.findViewById(R.id.rating_text_view);

        }

        void bindViewData(final TvShowEntity tvShowEntity){
            movieContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigateView(tvShowEntity);
                }
            });
            Glide.with(context)
                    .load(tvShowEntity.getImage())
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
            movieTitleText.setText(tvShowEntity.getTitle());
            movieReleaseDateText.setText(Utils.formatDate(tvShowEntity.getReleaseDate()));
            movieScore.setText(String.valueOf(tvShowEntity.getScore()));
        }

        private void navigateView(TvShowEntity tvShowEntity){
            Intent intent = new Intent(context, DetailFilmActivity.class);
            intent.putExtra(Constants.TV_SHOW_INTENT, tvShowEntity);
            context.startActivity(intent);
        }
    }
}
