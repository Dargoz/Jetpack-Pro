package com.dargoz.jetpack.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity(tableName = "movie")
public class MovieEntity implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "score")
    private double score;

    @ColumnInfo(name = "status")
    private String status;

    private int image;

    @ColumnInfo(name = "imagePath")
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

    public MovieEntity(String id, String title, String description, String releaseDate, String genre,
                       String duration, double score, String status, String imagePath) {
        this.id = Integer.parseInt(id);
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.score = score;
        this.status = status;
        this.imagePath = imagePath;
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

    public int getId() {
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

    public double getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public int getImage() {
        return image;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenre(ArrayList<GenreEntity> genreEntityArrayList) {
        StringBuilder genreString = new StringBuilder();
        for(GenreEntity genreEntity : genreEntityArrayList) {
            genreString.append(genreEntity.getName()).append(", ");
        }
        this.genre = genreString.substring(0, genreString.length()-2);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.releaseDate);
        dest.writeString(this.genre);
        dest.writeString(this.duration);
        dest.writeDouble(this.score);
        dest.writeString(this.status);
        dest.writeInt(this.image);
        dest.writeString(this.imagePath);
    }

    protected MovieEntity(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.genre = in.readString();
        this.duration = in.readString();
        this.score = in.readDouble();
        this.status = in.readString();
        this.image = in.readInt();
        this.imagePath = in.readString();
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
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
