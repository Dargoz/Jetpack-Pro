package com.dargoz.jetpack.data;

public class TvShowEntity extends MovieEntity {
    private int totalEpisode;

    public TvShowEntity(String title, String description, String genre, String duration, int totalEpisode) {
        super(title, description, genre, duration);
        this.totalEpisode = totalEpisode;
    }
}
