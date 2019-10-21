package com.dargoz.jetpack.data.source.remote.response;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShowResponse extends MovieResponse {
    private String episode;

    public TvShowResponse(JSONObject jsonObject){
        super();
        try {
            setMovieResponse(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("overview"),
                    jsonObject.getString("first_air_date"),
                    jsonObject.getString("vote_average"),
                    jsonObject.getString("poster_path")
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private TvShowResponse(Parcel in) {
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
