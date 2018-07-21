package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class Track {

    @PrimaryKey
    @NonNull
    private String id;

    private String albumId;

    private String artist;

    private int duration_ms;

    private String href;

    private String spotifyId;

    private String name;

    private int track_number;

    private String type;

    private String uri;

    private float speechiness;

    public float getSpeechiness() {
        return speechiness;
    }

    public Track(String id, String albumId, String artist, int duration_ms, String href, String spotifyId, String name, int track_number, String type, String uri, float speechiness) {
        this.id = id;
        this.albumId = albumId;
        this.artist = artist;
        this.duration_ms = duration_ms;
        this.href = href;
        this.spotifyId = spotifyId;
        this.name = name;
        this.track_number = track_number;
        this.type = type;
        this.uri = uri;
        this.speechiness = speechiness;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

//    public static Track[] populateData(){
//        return new Track[]{
//                new Track((long)1.0,"",0,"href","1", "name 1", 1,"type 1","uri 1",(float)0.2),
//                new Track((long)2.0,"",0,"href","2", "name 2", 2,"type 2","uri 2",(float)0.2),
//                new Track((long)3.0,"",0,"href","3", "name 3", 3,"type 3","uri 3",(float)0.2),
//                new Track((long)4.0,"",0,"href","4", "name 4", 4,"type 4","uri 4",(float)0.2),
//                new Track((long)5.0,"",0,"href","5", "name 5", 5,"type 5","uri 5",(float)0.2)
//        };
//    }
}
