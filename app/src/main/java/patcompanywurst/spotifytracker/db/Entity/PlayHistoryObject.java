package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PlayHistoryObject {
    @PrimaryKey
    private long playHistoryObjectId;

    //private Track track;
    private String objectContextType; // artist, playlist,album

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
}
