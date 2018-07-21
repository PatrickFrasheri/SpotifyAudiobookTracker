package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity
public class PlayHistoryObject {
    @PrimaryKey(autoGenerate = true)
    private long playHistoryObjectId;

    private long trackId;

    @ColumnInfo(name = "object_context_type")
    private String objectContextType; // artist, playlist,album

    public PlayHistoryObject(String objectContextType, long trackId) {
        this.objectContextType = objectContextType;
        this.trackId = trackId;
    }


    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getPlayHistoryObjectId() {
        return playHistoryObjectId;
    }

    public void setPlayHistoryObjectId(long playHistoryObjectId) {
        this.playHistoryObjectId = playHistoryObjectId;
    }

    public String getObjectContextType() {
        return objectContextType;
    }

    public void setObjectContextType(String objectContextType) {
        this.objectContextType = objectContextType;
    }


    public static PlayHistoryObject[] populateData() {
        return new PlayHistoryObject[] {
                new PlayHistoryObject("title1", 1),
                new PlayHistoryObject("title2",2),
                new PlayHistoryObject("title3",3),
                new PlayHistoryObject("title4",4),
                new PlayHistoryObject("title5",5)
        };
    }
}

