package dohack2018.spotifytracker.api.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyApiService {

    @GET("me/player/recently-played")
    Call<SpotifyTrackResponseList> getRecentlyPlayed(
            @Header("Authorization") String authHeader,
            @Query("limit") int limit
    );

    @GET("albums")
    Call<SpotifyAlbumList> getAlbums(
            @Header("Authorization") String authHeader,
            @Query("ids") String ids
    );

    @GET("audio-features")
    Call<SpotifyAudioFeaturesList> getAudioFeatures(
            @Header("Authorization") String authHeader,
            @Query("ids") String ids
    );

    @GET("tracks")
    Call<SpotifyFullTrackList> getTracks(
            @Header("Authorization") String authHeader,
            @Query("ids") String ids
    );

}
