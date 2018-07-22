package patcompanywurst.spotifytracker.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import patcompanywurst.spotifytracker.db.Entity.Album;
import patcompanywurst.spotifytracker.db.Entity.AudioFeature;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;
import patcompanywurst.spotifytracker.db.Entity.Track;

@Database(entities = {Album.class,Track.class, PlayHistoryObject.class, SpotifyCredentials.class, AudioFeature.class}, version =1, exportSchema = false)
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
//                                getInstance(context).audiobookDAO().addPlayHistoryObjects(PlayHistoryObject.populateData());
//                                getInstance(context).audiobookDAO().addTrack(Track.populateData());
//                                getInstance(context).audiobookDAO().addAlbums(Album.populateData());

                            }
                        });
                    }
                })
                .build();
    }

}
