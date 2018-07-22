package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import patcompanywurst.spotifytracker.db.Entity.Album;

public class SpotifyAlbumList {

    @SerializedName("items")
    private List<Album> items;

    public List<Album> getItems() {
        return items;
    }

}
