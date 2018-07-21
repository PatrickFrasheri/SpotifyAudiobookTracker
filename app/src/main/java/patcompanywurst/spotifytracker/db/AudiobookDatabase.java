package patcompanywurst.spotifytracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import patcompanywurst.spotifytracker.db.Entity.Artist;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;
import patcompanywurst.spotifytracker.db.Entity.Track;

@Database(entities = {Artist.class, Track.class, PlayHistoryObject.class}, version =1, exportSchema = false)
public abstract class AudiobookDatabase extends RoomDatabase {

    public abstract AudiobookDAO audiobookDAO();
}
