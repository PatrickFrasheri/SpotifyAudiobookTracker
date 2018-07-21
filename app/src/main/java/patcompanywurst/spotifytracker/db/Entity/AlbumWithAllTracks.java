package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class AlbumWithAllTracks {
    @Embedded
    public Album album;
    @Relation(parentColumn = "albumId",entityColumn = "albumId",entity = Track.class)
    public List<Track> tracks;
}
