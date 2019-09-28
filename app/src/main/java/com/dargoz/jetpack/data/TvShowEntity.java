package com.dargoz.jetpack.data;

public class TvShowEntity extends MovieEntity {
    private int totalEpisode;

    public TvShowEntity(String title, String description, String releaseDate, String genre,
                        String duration, double score, int image, int totalEpisode) {
        super(title, description, releaseDate, genre, duration, score, image);
        this.totalEpisode = totalEpisode;
    }
}
