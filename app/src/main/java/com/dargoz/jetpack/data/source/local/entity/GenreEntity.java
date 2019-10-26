package com.dargoz.jetpack.data.source.local.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class GenreEntity {
    private int id;
    private String name;

    public GenreEntity(JSONObject genreObject){
        try {
            this.id = genreObject.getInt("id");
            this.name = genreObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }
}
