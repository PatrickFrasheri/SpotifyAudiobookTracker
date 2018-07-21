package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

public class SpotifyTrackResponse {

    @SerializedName("track")
    private SpotifyTrack track;

    public SpotifyTrackResponse(SpotifyTrack track) {
        this.track = track;
    }

    public SpotifyTrack getTrack() {
        return track;
    }

    public void setTrack(SpotifyTrack track) {
        this.track = track;
    }
}
