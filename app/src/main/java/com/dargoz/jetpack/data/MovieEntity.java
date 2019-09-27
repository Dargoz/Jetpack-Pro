package com.dargoz.jetpack.data;

public class MovieEntity {
    private String title;
    private String description;
    private String genre;
    private String duration;
    private int image;

    public MovieEntity(String title, String description, String genre, String duration, int image) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }
}
