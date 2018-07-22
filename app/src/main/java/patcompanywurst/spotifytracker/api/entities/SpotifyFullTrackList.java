package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import patcompanywurst.spotifytracker.db.Entity.Track;

public class SpotifyFullTrackList {

    @SerializedName("tracks")
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

}
