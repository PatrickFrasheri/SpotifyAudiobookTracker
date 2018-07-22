package dohack2018.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class Track {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    private String albumId;

    @SerializedName("album")
    @Ignore
    private Album album;

    private String artist;

    @SerializedName("duration_ms")
    private int duration_ms;

    @SerializedName("href")
    private String href;

    @SerializedName("name")
    private String name;

    @SerializedName("track_number")
    private int track_number;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    private float speechiness;

    public float getSpeechiness() {
        return speechiness;
    }

    private String playedAt;

    public Track(String id, String albumId, String artist, int duration_ms, String href, String name, int track_number, String type, String uri, float speechiness) {
        this.id = id;
        this.albumId = albumId;
        this.artist = artist;
        this.duration_ms = duration_ms;
        this.href = href;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
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
