package patcompanywurst.spotifytracker.db.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class TrackWithAllPlayHistoryObjects {

    @Embedded
    public Track track;

    @Relation(parentColumn = "id", entityColumn = "trackId", entity = PlayHistoryObject.class)
    public List<PlayHistoryObject> playHistoryObject;



    public List<PlayHistoryObject> getPlayHistoryObject() {
        return playHistoryObject;
    }

    public void setPlayHistoryObject(List<PlayHistoryObject> playHistoryObject) {
        this.playHistoryObject = playHistoryObject;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

}
