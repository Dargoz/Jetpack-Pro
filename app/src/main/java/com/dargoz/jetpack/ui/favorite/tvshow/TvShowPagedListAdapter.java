package com.dargoz.jetpack.ui.favorite.tvshow;

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
import com.dargoz.jetpack.data.source.local.entity.TvShowEntity;
import com.dargoz.jetpack.ui.detail.DetailFilmActivity;
import com.dargoz.jetpack.utils.Constants;
import com.dargoz.jetpack.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;

import static com.dargoz.jetpack.utils.ImageRepositoryList.findImage;

public class TvShowPagedListAdapter extends PagedListAdapter<TvShowEntity, TvShowPagedListAdapter.TvShowViewHolder> {
    private Context context;

    private static final DiffUtil.ItemCallback<TvShowEntity> diffCallback = new DiffUtil.ItemCallback<TvShowEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    TvShowPagedListAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TvShowPagedListAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item_list, parent, false);
        context = itemRow.getContext();
        return new TvShowPagedListAdapter.TvShowViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowPagedListAdapter.TvShowViewHolder holder, int position) {
        TvShowEntity tvShowEntity = getItem(position);
        if(tvShowEntity != null) {
            holder.bindViewData(tvShowEntity);
        }
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder{
        private final ShimmerFrameLayout shimmerFrameLayout;
        private final FrameLayout tvShowContainer;
        private final ImageView tvShowPoster;
        private final TextView tvShowTitleText;
        private final TextView tvShowReleaseDateText;
        private final TextView tvShowScore;


        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.movie_item_shimmer_layout);
            tvShowContainer = itemView.findViewById(R.id.movie_image_container);
            tvShowPoster = itemView.findViewById(R.id.movie_image_view);
            tvShowTitleText = itemView.findViewById(R.id.movie_title_text_view);
            tvShowReleaseDateText = itemView.findViewById(R.id.movie_release_date_text_view);
            tvShowScore = itemView.findViewById(R.id.rating_text_view);

        }

        void bindViewData(final TvShowEntity tvShowEntity){
            Bitmap bitmap = findImage(tvShowEntity.getId());
            shimmerFrameLayout.startShimmer();
            if(bitmap != null){
                if(shimmerFrameLayout.isShimmerStarted()){
                    shimmerFrameLayout.stopShimmer();
                }
                shimmerFrameLayout.setVisibility(View.GONE);
            }
            tvShowContainer.setOnClickListener(view -> navigateView(tvShowEntity));
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
                    .into(tvShowPoster);
            tvShowTitleText.setText(tvShowEntity.getTitle());
            tvShowReleaseDateText.setText(Utils.formatDate(tvShowEntity.getReleaseDate()));
            tvShowScore.setText(String.valueOf(tvShowEntity.getScore()));
        }

        private void navigateView(TvShowEntity tvShowEntity){
            Intent intent = new Intent(context, DetailFilmActivity.class);
            intent.putExtra(Constants.TV_SHOW_INTENT, tvShowEntity);
            context.startActivity(intent);
        }
    }
}
