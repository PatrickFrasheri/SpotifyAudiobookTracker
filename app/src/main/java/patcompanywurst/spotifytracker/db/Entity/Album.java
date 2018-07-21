package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class Album {

    @PrimaryKey
    @NonNull
    private String id;

    private String artist;

    private String href;

    private String image;

    private String name;

    private String uri;

    public Album(String id, String href, String image, String name, String uri) {
        this.id = id;
        this.href = href;
        this.image = image;
        this.name = name;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String label) {
        this.name = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
//    public static Album[] populateData(){
//        return new Album[]{
//                new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY"),
//                new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY"),
//                new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY"),
//                new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY"),
//                new Album("","0sNOF9WDwhWunNAHPD3Baj","https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d","She's So Unusual", "spotify:artist:2BTZIqw0ntH9MvilQ3ewNY")
//        };
//    }
}
