package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.SpotifyCredentials;
import patcompanywurst.spotifytracker.db.Entity.Track;

public class ListViewActivity extends AppCompatActivity {

    ArrayList<Track> trackList;
    ArrayAdapter<Track> adapter;
    AudiobookDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        trackList = new ArrayList<Track>();
        adapter = new ArrayAdapter<Track>(this, R.layout.listview_item, R.id.textbox, trackList);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        new TrackDatabaseAsync(this).execute();
    }

    static class TrackDatabaseAsync extends AsyncTask<Void, Void, List<Track>> {

        private final WeakReference<Activity> weakMainActivity;

        TrackDatabaseAsync(Activity myActivity) {
            this.weakMainActivity = new WeakReference<>(myActivity);
        }

        @Override
        protected List<Track> doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakMainActivity.get().getApplicationContext());

            return database.audiobookDAO().getTrack();
            // Check if we have existing credentials
        }

        @Override
        protected void onPostExecute(List<Track> trackList) {

        }
        }
    }
