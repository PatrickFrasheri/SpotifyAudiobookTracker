package patcompanywurst.spotifytracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import patcompanywurst.spotifytracker.db.Entity.Artist;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;
import patcompanywurst.spotifytracker.db.Entity.Track;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AudiobookDAO {

    @Insert(onConflict = REPLACE)
    void addArtist(Artist artist);

    @Insert(onConflict = REPLACE)
    void addPlayHistoryObject(PlayHistoryObject playHistoryObject);

    @Insert(onConflict = REPLACE)
    void addTrack(Track track);

    @Query("SELECT * FROM Artist")
    List<Artist> getArtist();

    @Query("SELECT * FROM Track")
    List<Track> getTrack();

    @Query("SELECT * FROM PlayHistoryObject")
    List<PlayHistoryObject> getPlayHistoryObject();


}
