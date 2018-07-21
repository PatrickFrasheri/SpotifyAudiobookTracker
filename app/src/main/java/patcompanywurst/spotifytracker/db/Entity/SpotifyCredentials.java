package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity()
public class SpotifyCredentials
{
    public SpotifyCredentials(String accessToken) {
        this.accessToken = accessToken;
    }

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "access_token")
    String accessToken = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
