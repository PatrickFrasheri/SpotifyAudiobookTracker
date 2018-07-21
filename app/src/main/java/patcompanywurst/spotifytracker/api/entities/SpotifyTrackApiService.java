package patcompanywurst.spotifytracker.api.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SpotifyTrackApiService {

    @GET("me/player/recently-played")
    Call<SpotifyTrackResponseList> getRecentlyPlayed(@Header("Authorization") String authHeader);

}
