package dohack2018.spotifytracker;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.lang.ref.WeakReference;

import patcompanywurst.spotifytracker.R;
import dohack2018.spotifytracker.db.AudiobookDatabase;
import dohack2018.spotifytracker.db.Entity.SpotifyCredentials;

public class SpotifyAuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "SpotifyAuthentication";

    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "spotifyaudiobooktracker://callback";

    private static final String CLIENT_ID = "386aa23342d84133bb50a57f3b19116b";

    AudiobookDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_authentication);

        // ToDo: remove
        database = Room.databaseBuilder(getApplicationContext(),
                AudiobookDatabase.class, "audiobook").build();

        AuthenticationRequest.Builder builder;
        builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "user-read-recently-played"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginInBrowser(this, request);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    Log.i(TAG, "onNewIntent Response: TOKEN");
                    Log.i(TAG, String.format("Access Token: %s", response.getAccessToken()));
                    SpotifyCredentials credentials  = new SpotifyCredentials(response.getAccessToken());
                    new InsertCredentialsTask(this, credentials).execute();
                    break;
                // Auth flow returned an error
                case ERROR:
                    // ToDo: Handle error case
                    Log.i(TAG, "onNewIntent Response: ERROR");
                    // Handle error response
                    break;
                // Most likely auth flow was cancelled
                default:
                    // ToDo: Handle error case
                    Log.i(TAG, "onNewIntent Response: DEFAULT");
                    // Handle other cases
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    Log.i(TAG, "onActivityResult Response: TOKEN");
                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.i(TAG, "onActivityResult Response: ERROR");
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.i(TAG, "onActivityResult Response: DEFAULT");
                    // Handle other cases
            }
        }
    }

    static class InsertCredentialsTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<Activity> weakActivity;

        private SpotifyCredentials credentials;

        InsertCredentialsTask(Activity activity, SpotifyCredentials credentials) {
            this.weakActivity = new WeakReference<>(activity);
            this.credentials = credentials;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakActivity.get().getApplicationContext());

            // Delete old credentials
            database.spotifyCredentialsDao().deleteCredentials();
            // Insert new credentials
            database.spotifyCredentialsDao().insert(credentials);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            weakActivity
                    .get()
                    .startActivity(
                            new Intent(weakActivity.get().getApplicationContext(),
                                    //SpotifyAuthenticationActivity.class
                                    MainActivity.class
                            ));
        }

    }
}
