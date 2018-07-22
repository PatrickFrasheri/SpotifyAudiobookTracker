package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrack;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrackApiService;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrackResponseList;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrackResponse;
import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SpotifyPlayer.NotificationCallback {

    private static final String TAG = "AudioBookMainActivity";

    private static final String CLIENT_ID = "386aa23342d84133bb50a57f3b19116b";
    private Player mPlayer;

    public static final String BASE_URL = "https://api.spotify.com/v1/";
    private static Retrofit retrofit = null;

    AudiobookDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AudiobookDatabase.getInstance(getApplicationContext());

        Log.i(TAG, "Starting async history object fetch");

        new StartupTask(this).execute();
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.i(TAG, "Playback event received: " + playerEvent.name());
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.i(TAG, "Playback error received: " + error.name());
    }

    class StartupTask extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<Activity> weakMainActivity;

        StartupTask(Activity activity) {
            this.weakMainActivity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakMainActivity.get().getApplicationContext());

            SpotifyCredentials credentials = database.spotifyCredentialsDao().getCredentials();

            // We don't have credentials yet. Start authorization process in postExecute
            if (credentials == null) {
                return true;
            }

//            SpotifyAccess.setCredentials(credentials);

            // Start SpotifyAccess Player
            Config playerConfig = new Config(weakMainActivity.get().getApplicationContext(), credentials.getAccessToken(), CLIENT_ID);
            Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    mPlayer = spotifyPlayer;
                    mPlayer.addNotificationCallback(MainActivity.this);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });

            return false;
        }

        @Override
        protected void onPostExecute(Boolean missingCredentials) {
            super.onPostExecute(missingCredentials);

            if (missingCredentials) {
                Log.i(TAG, "Missing credentials. Starting SpotifyAccess Authorization");
                weakMainActivity
                        .get()
                        .startActivity(
                                new Intent(weakMainActivity.get().getApplicationContext(),
                                        SpotifyAuthenticationActivity.class
                                ));
            } else {
//                if (SpotifyAccess.getInstance() != null)
//                    SpotifyAccess.getInstance().getAlbumInformation("2dIGnmEIy1WZIcZCFSj6i8");
                new FetchData(weakMainActivity.get()).execute();
            }
        }
    }

    static class FetchData extends AsyncTask<Void, Void, Void> {

        private final WeakReference<Activity> weakMainActivity;


        FetchData(Activity activity) {
            this.weakMainActivity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakMainActivity.get().getApplicationContext());

            SpotifyCredentials credentials = database.spotifyCredentialsDao().getCredentials();

            // We don't have credentials yet
            if (credentials == null) {
                return null;
            }

            if (retrofit == null) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            SpotifyTrackApiService movieApiService = retrofit.create(SpotifyTrackApiService.class);
            Call<SpotifyTrackResponseList> call = movieApiService
                    .getRecentlyPlayed(String.format("Bearer %s", credentials.getAccessToken()));

            call.enqueue(new Callback<SpotifyTrackResponseList>() {
                @Override
                public void onResponse(Call<SpotifyTrackResponseList> call, Response<SpotifyTrackResponseList> response) {
                    List<SpotifyTrackResponse> trackResponses = response.body().getItems();
//                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                    Log.d(TAG, "Number of tracks received: " + trackResponses.size());

                    for (SpotifyTrackResponse trackResponse : trackResponses) {
                        SpotifyTrack track = trackResponse.getTrack();
                        Log.d(TAG, String.format("Track: %s - %s", track.getId(), track.getName()));
                    }
                }
                @Override
                public void onFailure(Call<SpotifyTrackResponseList> call, Throwable throwable) {
                    Log.e(TAG, throwable.toString());
                }
            });

//            SpotifyApi api = new SpotifyApi();
//            api.setAccessToken(credentials.getAccessToken());
//
//            SpotifyService service = api.getService();
//
//            kaaes.spotify.webapi.android.models.Album apiAlbum = service.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
//
//            Album album = new Album(apiAlbum.id,
//                    apiAlbum.href,
//                    apiAlbum.images.get(0).url,
//                    apiAlbum.name,
//                    apiAlbum.uri);
//
//            Album[] albums = new Album[1];
//
//            albums[0] = album;
//
//            database.audiobookDAO().addAlbum(albums);
//
//            Log.i(TAG, String.format("Album name: %s Artist: %s", apiAlbum.name, apiAlbum.artists.get(0)));
//
//            List<Album> fetchedAlbums = database.audiobookDAO().getAlbum();
//
//            Log.i(TAG, String.format("Found %d albums in database", fetchedAlbums.size()));
//            for (Album a : fetchedAlbums)
//                Log.i(TAG, String.format("Album name: %s", a.getName()));
//
//            Pager<Track> pager = service.getRecentlyPlayed();
//            for (Track track : pager.items)
//                Log.i(TAG, String.format("Pager Track: %s - %s", track.name, track.album));

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Intent intent = new Intent(weakMainActivity.get().getApplicationContext(),RecyclerViewActivity.class);
            weakMainActivity.get().startActivity(intent);
        }
    }

}
