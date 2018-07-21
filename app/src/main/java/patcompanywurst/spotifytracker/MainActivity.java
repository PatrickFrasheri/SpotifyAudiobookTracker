package patcompanywurst.spotifytracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.List;

import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AudioBookMainActivity";

    AudiobookDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        database = AudiobookDatabase.getInstance(getApplicationContext());

        Log.i(TAG, "Starting async history object fetch");
        new DatabaseAsync().execute();

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<PlayHistoryObject> playHistoryObjects = database.audiobookDAO().getPlayHistoryObject();

            Log.i(TAG, "Printing history objects");

            for (PlayHistoryObject object : playHistoryObjects)
                Log.i(TAG, String.format("Play History ID: %d", object.getPlayHistoryObjectId()));

            return null;
        }
    }

}
