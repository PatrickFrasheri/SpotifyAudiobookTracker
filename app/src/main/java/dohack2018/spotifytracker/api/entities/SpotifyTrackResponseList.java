package dohack2018.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyTrackResponseList {

    @SerializedName("items")
    private List<SpotifyTrackResponse> items;

    public List<SpotifyTrackResponse> getItems() {
        return items;
    }

}
