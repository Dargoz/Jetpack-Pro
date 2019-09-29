package com.dargoz.jetpack.data;

import android.os.Parcel;

public class TvShowEntity extends MovieEntity {
    private String totalEpisode;

    public TvShowEntity(String title, String description, String releaseDate, String genre,
                        String duration, double score, String status, int image, String totalEpisode) {
        super(title, description, releaseDate, genre, duration, score, status, image);
        this.totalEpisode = totalEpisode;
    }

    public String getTotalEpisode() {
        return totalEpisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.totalEpisode);
    }

    private TvShowEntity(Parcel in) {
        super(in);
        this.totalEpisode = in.readString();
    }

    public static final Creator<TvShowEntity> CREATOR = new Creator<TvShowEntity>() {
        @Override
        public TvShowEntity createFromParcel(Parcel source) {
            return new TvShowEntity(source);
        }

        @Override
        public TvShowEntity[] newArray(int size) {
            return new TvShowEntity[size];
        }
    };
}
