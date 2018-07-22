package dohack2018.spotifytracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import dohack2018.spotifytracker.db.Entity.AlbumWithAllTracks;
import dohack2018.spotifytracker.db.Entity.AudioFeature;
import dohack2018.spotifytracker.db.Entity.Track;
import dohack2018.spotifytracker.db.Entity.TrackWithAllPlayHistoryObjects;
import dohack2018.spotifytracker.db.Entity.Album;
import dohack2018.spotifytracker.db.Entity.PlayHistoryObject;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AudiobookDAO {

    @Insert(onConflict = REPLACE)
    void addAlbum(Album album);

    @Insert(onConflict = REPLACE)
    void addAlbums(Album[] album);

    @Insert(onConflict = REPLACE)
    void addPlayHistoryObjects(PlayHistoryObject[] playHistoryObject);

    @Insert(onConflict = REPLACE)
    void addAudioFeature(AudioFeature audioFeature);

    @Insert(onConflict = REPLACE)
    void addAudioFeatures(AudioFeature[] audioFeatures);

    @Insert(onConflict = REPLACE)
    void addTrack(Track track);

    @Insert(onConflict = REPLACE)
    void addTracks(Track[] track);

    @Query("SELECT * FROM Album")
    List<Album> getAlbum();

    @Query("SELECT * FROM Track")
    List<Track> getTracks();

    @Query("SELECT * FROM Track WHERE id LIKE :id")
    Track findTrack(String id);

    @Query("SELECT * FROM Track JOIN AudioFeature ON AudioFeature.id = Track.id WHERE AudioFeature.speechiness > 0.7")
    List<Track> getAudioBookTracks();

    @Query("SELECT * FROM Track JOIN AudioFeature ON AudioFeature.id = Track.id WHERE AudioFeature.speechiness > 0.7 AND albumId IS NULL")
    List<Track> getSimplifiedAudioBookTracks();

    @Query("SELECT * FROM PlayHistoryObject")
    List<PlayHistoryObject> getPlayHistoryObject();

    @Query("SELECT * FROM Track WHERE albumId LIKE :albumId ORDER BY playedAt DESC LIMIT 1")
    Track getLatestTrackForAlbumId(String albumId);

    @Transaction
    @Query("SELECT * FROM Album")
    public List<AlbumWithAllTracks> loadAlbumWithTracks();

    @Transaction
    @Query("SELECT * FROM Track")
    public List<TrackWithAllPlayHistoryObjects> loadTracksWithPlayHistoryObjects();

}
