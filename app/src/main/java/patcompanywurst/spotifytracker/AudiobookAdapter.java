package patcompanywurst.spotifytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import patcompanywurst.spotifytracker.db.Entity.Album;

/**
 * Created by sdonath on 16.06.2017.
 */

public class AudiobookAdapter extends RecyclerView.Adapter<AudiobookAdapter.ViewHolder> {

    private static final String TAG = AudiobookAdapter.class.getSimpleName();
    private ArrayList<Album> albumArrayList;

    public AudiobookAdapter(ArrayList<Album> albumArrayList){
        this.albumArrayList = albumArrayList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumText;

        public ViewHolder (View v)
        {
            super( v );
            albumImage = v.findViewById(R.id.album_image);
            albumText = v.findViewById(R.id.album_name);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Album album = albumArrayList.get(position);

        holder.albumText.setText(album.getName());

        Picasso.with(holder.albumImage.getContext())
                .load(album.getImage())
                .resize(400, 800)
                .centerCrop()
                .into(holder.albumImage);

    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }
}
