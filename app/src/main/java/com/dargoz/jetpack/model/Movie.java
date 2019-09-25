package com.dargoz.jetpack.model;

class Movie {
    private String title;
    private String description;
    private String genre;
    private String duration;

    Movie(String title, String description, String genre, String duration) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
    }
}
