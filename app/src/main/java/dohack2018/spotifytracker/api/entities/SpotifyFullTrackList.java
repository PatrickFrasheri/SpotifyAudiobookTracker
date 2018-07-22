package dohack2018.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dohack2018.spotifytracker.db.Entity.Track;

public class SpotifyFullTrackList {

    @SerializedName("tracks")
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

}
