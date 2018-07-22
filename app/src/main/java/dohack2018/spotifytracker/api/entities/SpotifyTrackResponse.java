package dohack2018.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import dohack2018.spotifytracker.db.Entity.Track;

public class SpotifyTrackResponse {

    @SerializedName("track")
    private Track track;

    @SerializedName("played_at")
    private String playedAt;

    public SpotifyTrackResponse(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }
}
