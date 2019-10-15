package com.dargoz.jetpack.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieEntity implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String releaseDate;
    private String genre;
    private String duration;
    private double score;
    private String status;
    private int image;
    private String imagePath;

    public MovieEntity(String title, String description, String releaseDate, String genre,
                       String duration, double score, String status, int image) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.score = score;
        this.status = status;
        this.image = image;
    }
    public MovieEntity(@NonNull JSONObject movieObject){
        try {
            this.id = movieObject.getInt("id");
            this.title = movieObject.getString("title");
            this.description = movieObject.getString("overview");
            this.releaseDate = movieObject.getString("release_date");
            this.score = movieObject.getDouble("vote_average");
            this.imagePath = movieObject.getString("poster_path");

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public double getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public int getImage() {
        return image;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.releaseDate);
        dest.writeString(this.genre);
        dest.writeString(this.duration);
        dest.writeDouble(this.score);
        dest.writeString(this.status);
        dest.writeInt(this.image);
    }

     MovieEntity(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.genre = in.readString();
        this.duration = in.readString();
        this.score = in.readDouble();
        this.status = in.readString();
        this.image = in.readInt();
    }

    public static final Parcelable.Creator<MovieEntity> CREATOR = new Parcelable.Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel source) {
            return new MovieEntity(source);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };
}
