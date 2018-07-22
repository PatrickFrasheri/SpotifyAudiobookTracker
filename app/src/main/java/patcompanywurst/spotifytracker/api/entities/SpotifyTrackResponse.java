package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import patcompanywurst.spotifytracker.db.Entity.Track;

public class SpotifyTrackResponse {

    @SerializedName("track")
    private Track track;

    public SpotifyTrackResponse(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
