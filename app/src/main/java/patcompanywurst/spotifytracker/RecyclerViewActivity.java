package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import patcompanywurst.spotifytracker.db.AudiobookDatabase;
import patcompanywurst.spotifytracker.db.Entity.Album;
import patcompanywurst.spotifytracker.db.Entity.AlbumWithAllTracks;
import patcompanywurst.spotifytracker.db.Entity.Track;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

public class RecyclerViewActivity extends Activity{
    public List<AlbumWithAllTracks> albumList = new ArrayList<>();
//    public List<Track> trackList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter audiobookAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.audiobook_recyclerview);
        Log.i(TAG,"OnCreate");
        new GetAudiobookAsyncTask(this).execute();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //albumList.add(new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY"));
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        audiobookAdapter = new AudiobookAdapter((ArrayList<AlbumWithAllTracks>) albumList, this);
        mRecyclerView.setAdapter(audiobookAdapter);
    }

    class GetAudiobookAsyncTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Activity> weakMainActivity;

        GetAudiobookAsyncTask(Activity activity) {
            this.weakMainActivity = new WeakReference<>(activity);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            AudiobookDatabase database = AudiobookDatabase
                    .getInstance(weakMainActivity.get().getApplicationContext());
            //Hole mir die Album und Track Daten aus der Datenbank
            albumList = database.audiobookDAO().loadAlbumWithTracks();

//            trackList = database.audiobookDAO().getTracks();
            for(AlbumWithAllTracks o : albumList){
                Track latestTrack = database.audiobookDAO().getLatestTrackForAlbumId(o.getAlbum().getId());
                o.setLatestTrack(latestTrack);
            }
            return null;
        }
    }
}
