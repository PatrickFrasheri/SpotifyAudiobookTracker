package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.PlayHistoryObject;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;

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

        new CheckCredentialsAsync(this).execute();
    }

    static class CheckCredentialsAsync extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<Activity> weakMainActivity;

        CheckCredentialsAsync(Activity myActivity) {
            this.weakMainActivity = new WeakReference<>(myActivity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakMainActivity.get().getApplicationContext());

            // Check if we have existing credentials
            SpotifyCredentials credentials = database.spotifyCredentialsDao().getCredentials();

            return credentials == null;
        }

        @Override
        protected void onPostExecute(Boolean startAuthorization) {
            super.onPostExecute(startAuthorization);

            if (startAuthorization) {
                Log.i(TAG, "Missing credentials. Starting Spotify Authorization");
                weakMainActivity
                        .get()
                        .startActivity(
                                new Intent(weakMainActivity.get().getApplicationContext(),
                                        SpotifyAuthenticationActivity.class
                                ));
            }
        }
    }

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
