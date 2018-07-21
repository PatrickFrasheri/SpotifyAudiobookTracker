package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class PlayHistoryObject {
    @PrimaryKey
    @NonNull
    private String id;

    private String trackId;

    @ColumnInfo(name = "object_context_type")
    private String objectContextType; // artist, playlist,album

    public PlayHistoryObject(String id, String objectContextType, String trackId) {
        this.id = id;
        this.objectContextType = objectContextType;
        this.trackId = trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectContextType() {
        return objectContextType;
    }

    public void setObjectContextType(String objectContextType) {
        this.objectContextType = objectContextType;
    }

//    public static PlayHistoryObject[] populateData() {
//        return new PlayHistoryObject[] {
//                new PlayHistoryObject("title1", 1),
//                new PlayHistoryObject("title2",2),
//                new PlayHistoryObject("title3",3),
//                new PlayHistoryObject("title4",4),
//                new PlayHistoryObject("title5",5)
//        };
//    }
}

