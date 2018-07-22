package patcompanywurst.spotifytracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import patcompanywurst.spotifytracker.db.Entity.Album;
import patcompanywurst.spotifytracker.db.Entity.AlbumWithAllTracks;

/**
 * Created by sdonath on 16.06.2017.
 */

public class AudiobookAdapter extends RecyclerView.Adapter<AudiobookAdapter.ViewHolder> {

    private static final String TAG = AudiobookAdapter.class.getSimpleName();
    private ArrayList<AlbumWithAllTracks> albumArrayList;

    private final WeakReference<Activity> weakActivity;

    public AudiobookAdapter(ArrayList<AlbumWithAllTracks> albumArrayList, Activity activity){
        this.albumArrayList = albumArrayList;
        this.weakActivity = new WeakReference<>(activity);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumText;
        Button gotoTrack;
        TextView trackName;

        public ViewHolder (View v)
        {
            super( v );
            albumImage = v.findViewById(R.id.album_image);
            albumText = v.findViewById(R.id.album_name);
            gotoTrack = v.findViewById(R.id.goto_track);
            trackName = v.findViewById(R.id.track_name);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlbumWithAllTracks album = albumArrayList.get(position);
        View.OnClickListener a = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Click " +albumArrayList.get(position).getLatestTrack().getUri());
               //open Link to Track
                //albumArrayList.get(position).getLatestTrack().getHref();
                String uri = albumArrayList.get(position).getLatestTrack().getUri();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                intent.putExtra(Intent.EXTRA_REFERRER,
                        Uri.parse("android-app://" + weakActivity.get().getApplicationContext().getPackageName()));
                weakActivity.get().startActivity(intent);
            }
        };
        holder.albumText.setText(album.getAlbum().getName());
        holder.trackName.setText(album.getLatestTrack().getName());
        holder.gotoTrack.setOnClickListener(a);
        Picasso.with(holder.albumImage.getContext())
                .load(album.getAlbum().getImage())
                .resize(200, 200)
                .centerCrop()
                .into(holder.albumImage);

    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }
}
