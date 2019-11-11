package com.dargoz.jetpack.data.source.local.entity;

import android.os.Parcel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "tvShow")
public class TvShowEntity extends MovieEntity {

    @ColumnInfo(name = "totalEpisode")
    private String totalEpisode;

    public TvShowEntity(String title, String description, String releaseDate, String genre,
                        String duration, double score, String status, int image, String totalEpisode) {
        super(title, description, releaseDate, genre, duration, score, status, image);
        this.totalEpisode = totalEpisode;
    }

    public TvShowEntity(String id, String title, String description, String releaseDate, String genre,
                       String duration, double score, String status, String imagePath) {
        super(id, title, description, releaseDate, genre, duration, score, status, imagePath);
    }

    public String getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(String totalEpisode) {
        this.totalEpisode = totalEpisode;
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
