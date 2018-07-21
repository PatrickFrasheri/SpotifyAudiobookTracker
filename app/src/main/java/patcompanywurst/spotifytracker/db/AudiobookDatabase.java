package patcompanywurst.spotifytracker.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import patcompanywurst.spotifytracker.db.Entity.Artist;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;
import patcompanywurst.spotifytracker.db.Entity.Track;

@Database(entities = {Artist.class, Track.class, PlayHistoryObject.class, SpotifyCredentials.class}, version =1, exportSchema = false)
public abstract class AudiobookDatabase extends RoomDatabase {

    public abstract AudiobookDAO audiobookDAO();

    public abstract SpotifyCredentialsDao spotifyCredentialsDao();

    private static AudiobookDatabase INSTANCE;

    public synchronized static AudiobookDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AudiobookDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AudiobookDatabase.class,
                "my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).audiobookDAO().addPlayHistoryObject(PlayHistoryObject.populateData());
                            }
                        });
                    }
                })
                .build();
    }

}
