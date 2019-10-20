package com.dargoz.jetpack.data.source.remote.response;

import android.os.Parcel;

public class TvShowResponse extends MovieResponse {
    private String episode;

    public TvShowResponse(String id, String title, String description, String releaseDate, String genre, String duration, String score, String status, String imagePath) {
        super(id, title, description, releaseDate, genre, duration, score, status, imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.episode);
    }

    protected TvShowResponse(Parcel in) {
        super(in);
        this.episode = in.readString();
    }

    public static final Creator<TvShowResponse> CREATOR = new Creator<TvShowResponse>() {
        @Override
        public TvShowResponse createFromParcel(Parcel source) {
            return new TvShowResponse(source);
        }

        @Override
        public TvShowResponse[] newArray(int size) {
            return new TvShowResponse[size];
        }
    };
}
