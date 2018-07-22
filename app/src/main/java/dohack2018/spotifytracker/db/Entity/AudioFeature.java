package dohack2018.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class AudioFeature {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("speechiness")
    private float speechiness;

    public AudioFeature(@NonNull String id, float speechiness) {
        this.id = id;
        this.speechiness = speechiness;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public float getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
    }
}
