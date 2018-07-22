package dohack2018.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dohack2018.spotifytracker.db.Entity.AudioFeature;

public class SpotifyAudioFeaturesList {

    @SerializedName("audio_features")
    private List<AudioFeature> items;

    public List<AudioFeature> getItems() {
        return items;
    }

}
