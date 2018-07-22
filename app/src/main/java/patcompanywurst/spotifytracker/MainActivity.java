package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import patcompanywurst.spotifytracker.api.entities.SpotifyApiService;
import patcompanywurst.spotifytracker.api.entities.SpotifyAudioFeaturesList;
import patcompanywurst.spotifytracker.api.entities.SpotifyFullTrackList;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrackResponseList;
import patcompanywurst.spotifytracker.api.entities.SpotifyTrackResponse;
import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.Album;
import patcompanywurst.spotifytracker.db.Entity.AudioFeature;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;
import patcompanywurst.spotifytracker.db.Entity.Track;
import retrofit2.Call;
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

        private AudiobookDatabase database;

        FetchData(Activity activity) {
            this.weakMainActivity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database = AudiobookDatabase
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

            SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
            Call<SpotifyTrackResponseList> call = spotifyApiService
                    .getRecentlyPlayed(String.format("Bearer %s", credentials.getAccessToken()), 50);

//            call.enqueue(new Callback<SpotifyTrackResponseList>() {
//                @Override
//                public void onResponse(Call<SpotifyTrackResponseList> call, Response<SpotifyTrackResponseList> response) {
//                    List<SpotifyTrackResponse> trackResponses = response.body().getItems();
////                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
//                    Log.d(TAG, "Number of tracks received: " + trackResponses.size());
//
//                    Track[] tracks = new Track[trackResponses.size()];
//                    int i = 0;
//                    for (SpotifyTrackResponse trackResponse : trackResponses) {
//                        Track track = trackResponse.getTracks();
//                        Log.d(TAG, String.format("Track: %s - %s", track.getId(), track.getName()));
//                        tracks[i++] = trackResponse.getTracks();
//                    }
//
//                    new AsyncTask<Track[], Void, Void>() {
//
//                        @Override
//                        protected Void doInBackground(Track[]... tracks) {
//
//                            for (Track[] trackArray : tracks)
//                                database.audiobookDAO().addTrack(trackArray);
//                            return null;
//                        }
//                    };
//                }
//                @Override
//                public void onFailure(Call<SpotifyTrackResponseList> call, Throwable throwable) {
//                    Log.e(TAG, throwable.toString());
//                }
//            });

            List<SpotifyTrackResponse> trackResponses = null;
            try {
                trackResponses = call.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (trackResponses == null) {
                Log.e(TAG, "Couldn't fetch track responses");
                return null;
            }

            Log.d(TAG, "Number of tracks received: " + trackResponses.size());

            Track[] tracks = new Track[trackResponses.size()];
            LinkedList<String> trackIds = new LinkedList<String>();
            int i = 0;
            for (SpotifyTrackResponse trackResponse : trackResponses) {
                Track track = trackResponse.getTrack();
                Log.d(TAG, String.format("Track: %s - %s", track.getId(), track.getName()));
                track.setPlayedAt(trackResponse.getPlayedAt());
                tracks[i++] = track;
                if (!trackIds.contains(track.getId()) && database.audiobookDAO().findTrack(track.getId()) == null)
                    trackIds.add(track.getId());
            }

            database.audiobookDAO().addTracks(tracks);

            String trackIdQueryString = TextUtils.join(",", trackIds);

            Call<SpotifyAudioFeaturesList> audioFeaturesCall = spotifyApiService
                .getAudioFeatures(String.format("Bearer %s", credentials.getAccessToken()), trackIdQueryString);

            List<AudioFeature> audioFeatures = null;
            try {
                audioFeatures = audioFeaturesCall.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (audioFeatures == null) {
                Log.e(TAG, "Couldn't fetch audio feature responses");
                return null;
            }

            Log.d(TAG, "Number of audio features received: " + audioFeatures.size());

            // Empty Audio feature responses result in "audio_features: [ null ]" #majorfuckup
            if (audioFeatures.size() > 1 || audioFeatures.get(0) != null) {
                AudioFeature[] audioFeatureArray = new AudioFeature[audioFeatures.size()];

                int j = 0;
                for (AudioFeature audioFeature : audioFeatures) {
                    audioFeatureArray[j++] = audioFeature;
                    Log.d(TAG, String.format("AudioFeature: Speechiness %f", audioFeature.getSpeechiness()));
                }

                database.audiobookDAO().addAudioFeatures(audioFeatureArray);
            }

            List<Track> simplifiedAudioBookTracks = database.audiobookDAO().getSimplifiedAudioBookTracks();
            trackIds = new LinkedList<String>();
            for (Track simplifiedAudioBookTrack : simplifiedAudioBookTracks) {
                trackIds.add(simplifiedAudioBookTrack.getId());
            }

            trackIdQueryString = TextUtils.join(",", trackIds);

            Call<SpotifyFullTrackList> tracksCall = spotifyApiService
                    .getTracks(String.format("Bearer %s", credentials.getAccessToken()), trackIdQueryString);

            List<Track> fullTracks = null;
            try {
                fullTracks = tracksCall.execute().body().getTracks();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fullTracks == null) {
                Log.e(TAG, "Couldn't fetch full tracks");
                return null;
            }

            int k = 0;
            Track[] fullTrackArray = new Track[fullTracks.size()];
            Map<String, Album> albumMap = new HashMap<String, Album>();
            for (Track track : fullTracks) {
//                Track track = trackResponse.getTrack();
//                if (albumMap.get(track.getAlbum().getId()) == null && track.getAlbum() != null)
//                    albumMap.put(track.getAlbum().getId(), track.getAlbum());
                database.audiobookDAO().addAlbum(track.getAlbum());

                track.setAlbumId(track.getAlbum().getId());
                Log.d(TAG, String.format("Full Track: %s - %s (Album: %s)", track.getId(), track.getName(), track.getAlbumId()));
                fullTrackArray[k++] = track;
            }

            database.audiobookDAO().addTracks(fullTrackArray);

//            Album[] albumArray = new Album[albumMap.size()];
//            int l = 0;
//            for (Album album : albumMap.values())
//                albumArray[l] = album;

//            database.audiobookDAO().addAlbums(albumArray);

//            Call<SpotifyAlbumList> albumCall = spotifyApiService
//                    .getAlbums(String.format("Bearer %s", credentials.getAccessToken()), albumIdQueryString);
//
//            List<Album> albums = null;
//            try {
//                albums = albumCall.execute().body().getItems();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (albums == null) {
//                Log.e(TAG, "Couldn't fetch album responses");
//                return null;
//            }
//
//            for (Album album : albums)
//                Log.i(TAG, String.format("Album: %s", album.getName()));

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
