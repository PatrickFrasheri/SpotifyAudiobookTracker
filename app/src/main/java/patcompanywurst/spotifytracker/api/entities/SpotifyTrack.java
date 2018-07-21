package patcompanywurst.spotifytracker.api.entities;

import com.google.gson.annotations.SerializedName;

public class SpotifyTrack {

    @SerializedName("id")
    private String id;

    @SerializedName("duration_ms")
    private int durationMs;

    @SerializedName("name")
    private String name;

    public SpotifyTrack(String id, int durationMs, String name) {
        this.id = id;
        this.durationMs = durationMs;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
