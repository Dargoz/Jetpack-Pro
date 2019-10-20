package com.dargoz.jetpack.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieResponse implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String releaseDate;
    private String genre;
    private String duration;
    private String score;
    private String status;
    private String imagePath;

    public MovieResponse(String id, String title, String description, String releaseDate,
                         String genre, String duration, String score, String status,
                         String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.score = score;
        this.status = status;
        this.imagePath = imagePath;
    }

    public MovieResponse(@NonNull JSONObject movieObject){
        try {
            this.id = movieObject.getString(    "id");
            this.title = movieObject.getString("title");
            this.description = movieObject.getString("overview");
            this.releaseDate = movieObject.getString("release_date");
            this.score = movieObject.getString("vote_average");
            this.imagePath = movieObject.getString("poster_path");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public String getImagePath() {
        return imagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.releaseDate);
        dest.writeString(this.genre);
        dest.writeString(this.duration);
        dest.writeString(this.score);
        dest.writeString(this.status);
        dest.writeString(this.imagePath);
    }

    MovieResponse(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.genre = in.readString();
        this.duration = in.readString();
        this.score = in.readString();
        this.status = in.readString();
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}
