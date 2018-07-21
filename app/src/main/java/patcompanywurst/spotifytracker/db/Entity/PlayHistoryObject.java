package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PlayHistoryObject {
    @PrimaryKey
    private long playHistoryObjectId;

    //private Track track;
    private String objectContextType; // artist, playlist,album

    public PlayHistoryObject(long playHistoryObjectId, String objectContextType) {
        this.playHistoryObjectId = playHistoryObjectId;
        this.objectContextType = objectContextType;
    }

    public long getPlayHistoryObjectId() {
        return playHistoryObjectId;
    }

    public void setPlayHistoryObjectId(long playHistoryObjectId) {
        this.playHistoryObjectId = playHistoryObjectId;
    }

//    public Track getTrack() {
//        return track;
//    }
//
//    public void setTrack(Track track) {
//        this.track = track;
//    }

    public String getObjectContextType() {
        return objectContextType;
    }

    public void setObjectContextType(String objectContextType) {
        this.objectContextType = objectContextType;
    }


    public static PlayHistoryObject[] populateData() {
        return new PlayHistoryObject[] {
                new PlayHistoryObject(1, "title1"),
                new PlayHistoryObject(2, "title2"),
                new PlayHistoryObject(3, "title3"),
                new PlayHistoryObject(4, "title4"),
                new PlayHistoryObject(5, "title5")
        };
    }
}
