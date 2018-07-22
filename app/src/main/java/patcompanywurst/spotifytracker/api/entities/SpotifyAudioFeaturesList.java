package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import patcompanywurst.spotifytracker.db.Entity.Album;
import patcompanywurst.spotifytracker.db.Entity.AudioFeature;

public class SpotifyAudioFeaturesList {

    @SerializedName("audio_features")
    private List<AudioFeature> items;

    public List<AudioFeature> getItems() {
        return items;
    }

}
