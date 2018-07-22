package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.List;

public class AlbumWithAllTracks {
    @Embedded
    public Album album;
    @Relation(parentColumn = "id",entityColumn = "albumId",entity = Track.class)
    public List<Track> tracks;

    @Ignore
    private Track latestTrack;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Track getLatestTrack() {
        return latestTrack;
    }

    public void setLatestTrack(Track latestTrack) {
        this.latestTrack = latestTrack;
    }
}
