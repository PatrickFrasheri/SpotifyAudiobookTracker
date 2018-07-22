package dohack2018.spotifytracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import dohack2018.spotifytracker.db.Entity.SpotifyCredentials;

@Dao
public interface SpotifyCredentialsDao {

    @Insert
    void insert(SpotifyCredentials credentials);

    @Query("DELETE FROM SpotifyCredentials")
    void deleteCredentials();

    @Query("SELECT * FROM SpotifyCredentials LIMIT 1")
    SpotifyCredentials getCredentials();

}
