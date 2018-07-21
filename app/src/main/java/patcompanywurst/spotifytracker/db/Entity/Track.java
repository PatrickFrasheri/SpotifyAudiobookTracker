package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity
public class Track {
    @PrimaryKey
    private long trackId;
    //private List<Artist> artist;
    private int duration_ms;
    private String href;
    private String spotifyId;
    private String name;
    private int track_number;
    private String type;
    private String uri;

    public float getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
    }

    private float speechiness;

    public long getId() {
        return trackId;
    }

    public void setId(long trackId) {
        this.trackId = trackId;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

//    public List<Artist> getArtist() {
//        return artist;
//    }
//
//    public void setArtist(List<Artist> artist) {
//        this.artist = artist;
//    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }
}
