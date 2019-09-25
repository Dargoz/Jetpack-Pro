package com.dargoz.jetpack.model;

public class TvShow extends Movie {
    private int totalEpisode;

    public TvShow(String title, String description, String genre, String duration, int totalEpisode) {
        super(title, description, genre, duration);
        this.totalEpisode = totalEpisode;
    }
}
